package data

import (
	"context"
	"database/sql"
	"errors"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type BodyTemperature struct {
	ID          int64     `json:"id"`
	Temperature float32   `json:"temperature"`
	CreatedAt   time.Time `json:"created_at"`
}

type BodyTemperatureModel struct {
	DB *sql.DB
}

func ValidateBodyTemperature(v *validator.Validator, bt *BodyTemperature) {
	v.Check(bt.Temperature != 0, "temperature", "must be provided")
	v.Check(bt.Temperature > 0, "temperature", "must be a positive integer")
	v.Check(bt.Temperature < 50, "temperature", "cannot be higher then 50 ºC")
}

func (m BodyTemperatureModel) Insert(bt *BodyTemperature) error {
	query := `
		INSERT INTO body_temperature (temperature)
		VALUES ($1)	
		RETURNING id, created_at`

	args := []any{bt.Temperature}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	return m.DB.QueryRowContext(ctx, query, args...).Scan(&bt.ID, &bt.CreatedAt)
}

func (m BodyTemperatureModel) Get(id int64) (*BodyTemperature, error) {
	if id < 1 {
		return nil, ErrRecordNotFound
	}

	query := `
		SELECT id, created_at, temperature 
		FROM body_temperature 
		WHERE id = $1`

	var bt BodyTemperature

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)

	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, id).Scan(
		&bt.ID,
		&bt.CreatedAt,
		&bt.Temperature,
	)

	if err != nil {
		switch {
		case errors.Is(err, sql.ErrNoRows):
			return nil, ErrRecordNotFound
		default:
			return nil, err
		}
	}

	return &bt, nil
}
