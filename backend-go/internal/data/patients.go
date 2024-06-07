package data

import (
	"context"
	"database/sql"
	"errors"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
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
        FROM patients 
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
