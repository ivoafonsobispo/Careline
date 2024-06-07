package main

import (
	"errors"
	"net/http"

	"github.com/ivoafonsobispo/careline/backend/internal/data"
	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

func (app *application) registerPatientHandler(w http.ResponseWriter, r *http.Request) {
	var input struct {
		Name         string `json:"name"`
		Email        string `json:"email"`
		Password     string `json:"password"`
		Sex          string `json:"sex"`
		HealthNumber string `json:"health_number"`
	}

	err := app.readJSON(w, r, &input)
	if err != nil {
		app.badRequestResponse(w, r, err)
		return
	}

	patient := &data.Patient{
		Name:         input.Name,
		Email:        input.Email,
		Sex:          input.Sex,
		HealthNumber: input.HealthNumber,
		Activated:    false,
	}

	err = patient.Password.Set(input.Password)
	if err != nil {
		app.serverErrorResponse(w, r, err)
		return
	}

	v := validator.New()

	if data.ValidatePatient(v, patient); !v.Valid() {
		app.failedValidationResponse(w, r, v.Errors)
		return
	}

	err = app.models.Patients.Insert(patient)
	if err != nil {
		switch {
		case errors.Is(err, data.ErrDuplicateEmail):
			v.AddError("email", "a user with this email address already exists")
			app.failedValidationResponse(w, r, v.Errors)
		default:
			app.serverErrorResponse(w, r, err)
		}
		return
	}

	err = app.writeJSON(w, http.StatusCreated, envelope{"patient": patient}, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}
