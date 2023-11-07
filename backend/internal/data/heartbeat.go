package data

import (
	"context"
	"database/sql"
	"errors"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type Heartbeat struct {
	ID        int64     `json:"id"`
	Heartbeat int32     `json:"heartbeat"`
	CreatedAt time.Time `json:"created_at"`
}

type HeartbeatModel struct {
	DB *sql.DB
}

func ValidateHeartbeat(v *validator.Validator, heartbeat *Heartbeat) {
	v.Check(heartbeat.Heartbeat != 0, "heartbeat", "must be provided")
	v.Check(heartbeat.Heartbeat > 0, "heartbeat", "must be a positive integer")
	v.Check(heartbeat.Heartbeat < 300, "heartbeat", "cannot be higher then 300BPM")
}

func (m HeartbeatModel) Insert(h *Heartbeat) error {
	query := `
		INSERT INTO heartbeat (heartbeat)
		VALUES ($1)	
		RETURNING id, created_at`

	args := []any{h.Heartbeat}

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	return m.DB.QueryRowContext(ctx, query, args...).Scan(&h.ID, &h.CreatedAt)
}

func (m HeartbeatModel) Get(id int64) (*Heartbeat, error) {
	if id < 1 {
		return nil, ErrRecordNotFound
	}

	query := `
		SELECT id, created_at, heartbeat
		FROM heartbeat
		WHERE id = $1`

	var h Heartbeat

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)

	defer cancel()

	err := m.DB.QueryRowContext(ctx, query, id).Scan(
		&h.ID,
		&h.CreatedAt,
		&h.Heartbeat,
	)

	if err != nil {
		switch {
		case errors.Is(err, sql.ErrNoRows):
			return nil, ErrRecordNotFound
		default:
			return nil, err
		}
	}

	return &h, nil
}
