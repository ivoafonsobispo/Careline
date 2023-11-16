package data

import (
	"context"
	"database/sql"
	"errors"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
	"golang.org/x/crypto/bcrypt"
)

type Patient struct {
	ID           int64     `json:"id"`
	CreatedAt    time.Time `json:"created_at"`
	Name         string    `json:"name"`
	Email        string    `json:"email"`
	Password     password  `json:"-"`
	Sex          string    `json:"sex"`
	HealthNumber string    `json:"health_number"`
	Activated    bool      `json:"activated"`
	Version      int       `json:"-"`
}

type PatientModel struct {
	DB *sql.DB
}

type password struct {
	plaintext *string
	hash      []byte
}

var (
	ErrDuplicateEmail = errors.New("duplicate email")
)

func (p *password) Set(plaintextPassword string) error {
	hash, err := bcrypt.GenerateFromPassword([]byte(plaintextPassword), 12)
	if err != nil {
		return err
	}

	p.plaintext = &plaintextPassword
	p.hash = hash

	return nil
}

func (p *password) Matches(plaintextPassword string) (bool, error) {
	err := bcrypt.CompareHashAndPassword(p.hash, []byte(plaintextPassword))
	if err != nil {
		switch {
		case errors.Is(err, bcrypt.ErrMismatchedHashAndPassword):
			return false, nil
		default:
			return false, err
		}
	}

	return true, nil
}

func ValidateEmail(v *validator.Validator, email string) {
	v.Check(email != "", "email", "must be provided")
	v.Check(validator.Matches(email, validator.EmailRX), "email", "must be a valid email address")
}

func ValidateHealthNumber(v *validator.Validator, healthNumber string) {
	v.Check(healthNumber != "", "health_number", "must be provided")
	v.Check(len(healthNumber) == 9, "health_number", "health number must be 9 digits")
}

func ValidatePasswordPlaintext(v *validator.Validator, password string) {
	v.Check(password != "", "password", "must be provided")
	v.Check(len(password) >= 8, "password", "must be at least 8 bytes long")
	v.Check(len(password) <= 72, "password", "must not be more than 72 bytes long")
}

func ValidateSex(v *validator.Validator, sex string) {
	v.Check(sex == "", "sex", "must be provided")
	v.Check(len(sex) == 1, "sex", "must be correctly provided")
	v.Check((sex == "F") || (sex == "M"), "sex", "invalid sex")
}

func ValidatePatient(v *validator.Validator, patient *Patient) {
	v.Check(patient.Name != "", "name", "must be provided")
	v.Check(len(patient.Name) <= 500, "name", "must not be more than 500 bytes long")

	ValidateEmail(v, patient.Email)

	ValidateHealthNumber(v, patient.HealthNumber)

	if patient.Password.plaintext != nil {
		ValidatePasswordPlaintext(v, *patient.Password.plaintext)
	}

	if patient.Password.hash == nil {
		panic("missing password hash for user")
	}
}

func (m PatientModel) Insert(patient *Patient) error {
	query := `
        INSERT INTO patients (name, email, password_hash, sex, health_number, activated) 
        VALUES ($1, $2, $3, $4, $5, $6)
        RETURNING id, created_at, version`

	args := []any{patient.Name, patient.Email, patient.Password.hash, patient.Sex, patient.HealthNumber, patient.Activated}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, args...).Scan(&patient.ID, &patient.CreatedAt, &patient.Version)
	if err != nil {
		switch {
		case err.Error() == `pq: duplicate key value violates unique constraint "users_email_key"`:
			return ErrDuplicateEmail
		default:
			return err
		}
	}

	return nil
}

func (m PatientModel) GetByEmail(email string) (*Patient, error) {
	query := `
        SELECT id, created_at, name, email, password_hash, sex, health_number,activated, version
        FROM users
        WHERE email = $1`

	var patient Patient

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, email).Scan(
		&patient.ID,
		&patient.CreatedAt,
		&patient.Name,
		&patient.Email,
		&patient.Password.hash,
		&patient.Activated,
		&patient.Version,
	)

	if err != nil {
		switch {
		case errors.Is(err, sql.ErrNoRows):
			return nil, ErrRecordNotFound
		default:
			return nil, err
		}
	}

	return &patient, nil
}

func (m PatientModel) Update(patient *Patient) error {
	query := `
        UPDATE patients 
        SET name = $1, email = $2, password_hash = $3, activated = $4, version = version + 1
        WHERE id = $5 AND version = $6
        RETURNING version`

	args := []any{
		patient.Name,
		patient.Email,
		patient.Password.hash,
		patient.Activated,
		patient.ID,
		patient.Version,
	}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, args...).Scan(&patient.Version)
	if err != nil {
		switch {
		case err.Error() == `pq: duplicate key value violates unique constraint "users_email_key"`:
			return ErrDuplicateEmail
		case errors.Is(err, sql.ErrNoRows):
			return ErrEditConflict
		default:
			return err
		}
	}

	return nil
}
