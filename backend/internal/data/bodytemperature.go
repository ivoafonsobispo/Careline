package data

import (
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type BodyTemperature struct {
	ID          int64     `json:"id"`
	Temperature float32   `json:"temperature"`
	CreatedAt   time.Time `json:"created_at"`
}

func ValidateBodyTemperature(v *validator.Validator, bt *BodyTemperature) {
	v.Check(bt.Temperature != 0, "temperature", "must be provided")
	v.Check(bt.Temperature > 0, "temperature", "must be a positive integer")
	v.Check(bt.Temperature < 50, "temperature", "cannot be higher then 50 ºC")

	//TODO: Validate User
}
