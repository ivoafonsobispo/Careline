package data

import (
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type Heartbeat struct {
	ID        int64     `json:"id"`
	Heartbeat int32     `json:"heartbeat"`
	CreatedAt time.Time `json:"created_at"`
}

func ValidateHeartbeat(v *validator.Validator, heartbeat *Heartbeat) {
	v.Check(heartbeat.Heartbeat != 0, "heartbeat", "must be provided")
	v.Check(heartbeat.Heartbeat > 0, "heartbeat", "must be a positive integer")
	v.Check(heartbeat.Heartbeat < 300, "heartbeat", "cannot be higher then 300BPM")
}
