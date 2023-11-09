package data

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
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

func (m HeartbeatModel) GetAll(filters Filters) ([]*Heartbeat, Metadata, error) {
	query := fmt.Sprintf(`
        SELECT count(*) OVER(), id, created_at,heartbeat 
        FROM heartbeat    
        ORDER BY %s %s, id ASC
        LIMIT $1 OFFSET $2`, filters.sortColumn(), filters.sortDirection())

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	args := []any{filters.limit(), filters.offset()}

	rows, err := m.DB.QueryContext(ctx, query, args...)
	if err != nil {
		return nil, Metadata{}, err
	}

	defer rows.Close()

	totalRecords := 0
	heartbeats := []*Heartbeat{}

	for rows.Next() {
		var heartbeat Heartbeat

		err := rows.Scan(
			&totalRecords,
			&heartbeat.ID,
			&heartbeat.CreatedAt,
			&heartbeat.Heartbeat,
		)

		if err != nil {
			return nil, Metadata{}, err
		}

		heartbeats = append(heartbeats, &heartbeat)
	}

	if err = rows.Err(); err != nil {
		return nil, Metadata{}, err
	}

	metadata := calculateMetadata(totalRecords, filters.Page, filters.PageSize)

	return heartbeats, metadata, nil
}
