package main

import (
	"errors"
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

	err = app.models.BodyTemperatures.Insert(bt)
	if err != nil {
		app.serverErrorResponse(w, r, err)
		return
	}

	headers := make(http.Header)
	headers.Set("Location", fmt.Sprintf("/v1/bodytemperature/%d", bt.ID))

	err = app.writeJSON(w, http.StatusCreated, envelope{"body_temperature": bt}, headers)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}

func (app *application) showBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {
	id, err := app.readIDParam(r)
	if err != nil {
		app.notFoundResponse(w, r)
		return
	}

	bt, err := app.models.BodyTemperatures.Get(id)
	if err != nil {
		switch {
		case errors.Is(err, data.ErrRecordNotFound):
			app.notFoundResponse(w, r)
		default:
			app.serverErrorResponse(w, r, err)
		}
		return
	}

	err = app.writeJSON(w, http.StatusOK, envelope{"body_temperature": bt}, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}

}

func (app *application) updateBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {

}

func (app *application) deleteBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {

}
