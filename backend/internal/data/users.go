package data

import (
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

type User struct {
	ID        int64     `json:"id"`
	FirstName string    `json:"first_name"`
	LastName  string    `json:"last_name"`
	CreatedAt time.Time `json:"-"`
}

func ValidateUser(v *validator.Validator, user *User) {
	v.Check(user.FirstName != "", "first_name", "must be provided")
	v.Check(len(user.FirstName) <= 20, "first_name", "must not be more than 20 characters long")

	v.Check(user.LastName != "", "first_name", "must be provided")
	v.Check(len(user.LastName) <= 20, "first_name", "must not be more than 20 characters long")
}
