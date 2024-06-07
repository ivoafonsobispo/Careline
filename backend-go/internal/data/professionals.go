package data

import (
	"context"
	"database/sql"
	"errors"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type Professional struct {
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

type ProfessionalModel struct {
	DB *sql.DB
}

func ValidateHealthNumberProfessional(v *validator.Validator, healthNumber string) {
	v.Check(healthNumber[0] == '9', "health_number", "must start with 9")
}

func ValidateProfessional(v *validator.Validator, professional *Professional) {
	v.Check(professional.Name != "", "name", "must be provided")
	v.Check(len(professional.Name) <= 500, "name", "must not be more than 500 bytes long")

	ValidateEmail(v, professional.Email)

	ValidateHealthNumber(v, professional.HealthNumber)
	ValidateHealthNumberProfessional(v, professional.HealthNumber)

	if professional.Password.plaintext != nil {
		ValidatePasswordPlaintext(v, *professional.Password.plaintext)
	}

	if professional.Password.hash == nil {
		panic("missing password hash for user")
	}
}

func (m ProfessionalModel) Insert(professional *Professional) error {
	query := `
        INSERT INTO professionals (name, email, password_hash, sex, health_number, activated) 
        VALUES ($1, $2, $3, $4, $5, $6)
        RETURNING id, created_at, version`

	args := []any{professional.Name, professional.Email, professional.Password.hash, professional.Sex, professional.HealthNumber, professional.Activated}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, args...).Scan(&professional.ID, &professional.CreatedAt, &professional.Version)
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

func (m ProfessionalModel) GetByEmail(email string) (*Professional, error) {
	query := `
        SELECT id, created_at, name, email, password_hash, sex, health_number,activated, version
        FROM professionals
        WHERE email = $1`

	var professional Professional

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, email).Scan(
		&professional.ID,
		&professional.CreatedAt,
		&professional.Name,
		&professional.Email,
		&professional.Password.hash,
		&professional.Activated,
		&professional.Version,
	)

	if err != nil {
		switch {
		case errors.Is(err, sql.ErrNoRows):
			return nil, ErrRecordNotFound
		default:
			return nil, err
		}
	}

	return &professional, nil
}

func (m ProfessionalModel) Update(professional *Professional) error {
	query := `
        UPDATE professionals 
        SET name = $1, email = $2, password_hash = $3, activated = $4, version = version + 1
        WHERE id = $5 AND version = $6
        RETURNING version`

	args := []any{
		professional.Name,
		professional.Email,
		professional.Password.hash,
		professional.Activated,
		professional.ID,
		professional.Version,
	}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, args...).Scan(&professional.Version)
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
