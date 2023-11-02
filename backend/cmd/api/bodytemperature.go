package main

import (
	"fmt"
	"net/http"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/data"
	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

func (app *application) createBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {
	var input struct {
		Temperature float32   `json:"temperature"`
		CreatedAt   time.Time `json:"created_at"`
	}

	err := app.readJSON(w, r, &input)
	if err != nil {
		app.badRequestResponse(w, r, err)
		return
	}

	bt := &data.BodyTemperature{
		Temperature: input.Temperature,
	}

	v := validator.New()

	if data.ValidateBodyTemperature(v, bt); !v.Valid() {
		app.failedValidationResponse(w, r, v.Errors)
		return
	}

	fmt.Fprintf(w, "%+v\n", input)
}

func (app *application) showBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {
	id, err := app.readIDParam(r)
	if err != nil {
		app.notFoundResponse(w, r)
		return
	}

	bt := data.BodyTemperature{
		ID:          id,
		CreatedAt:   time.Now(),
		Temperature: 36.4,
		User: data.User{
			ID:        548,
			FirstName: "José",
			LastName:  "Areia",
		},
	}

	err = app.writeJSON(w, http.StatusOK, envelope{"body_temperature": bt}, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}

}
