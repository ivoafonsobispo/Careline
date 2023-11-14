package main

import (
	"errors"
	"fmt"
	"net/http"

	"github.com/ivoafonsobispo/careline/backend/internal/data"
	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

func (app *application) createBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {
	var input struct {
		Temperature float32 `json:"temperature"`
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

func (app *application) listBodytemperatureHandler(w http.ResponseWriter, r *http.Request) {
	var input struct {
		data.Filters
	}

	v := validator.New()

	qs := r.URL.Query()

	input.Filters.Page = app.readInt(qs, "page", 1, v)
	input.Filters.PageSize = app.readInt(qs, "page_size", 20, v)
	input.Filters.Sort = app.readString(qs, "sort", "id")

	input.Filters.SortSafeList = []string{"id", "date", "-id", "-date"}

	if data.ValidateFilters(v, input.Filters); !v.Valid() {
		app.failedValidationResponse(w, r, v.Errors)
		return
	}

	bts, metadata, err := app.models.BodyTemperatures.GetAll(input.Filters)

	if err != nil {
		app.serverErrorResponse(w, r, err)
		return
	}

	err = app.writeJSON(w, http.StatusOK, envelope{"body_temperatures": bts, "metadata": metadata}, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}
