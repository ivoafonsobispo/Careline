package main

import (
	"fmt"
	"net/http"
	"time"

	"github.com/ivoafonsobispo/careline/backend/internal/data"
	"github.com/ivoafonsobispo/careline/backend/internal/validator"
)

func (app *application) createHeartbeatHandler(w http.ResponseWriter, r *http.Request) {
	var input struct {
		Heartbeat int32     `json:"heartbeat"`
		CreatedAt time.Time `json:"created_at"`
	}

	err := app.readJSON(w, r, &input)
	if err != nil {
		app.badRequestResponse(w, r, err)
		return
	}

	h := &data.Heartbeat{
		Heartbeat: input.Heartbeat,
	}

	v := validator.New()

	if data.ValidateHeartbeat(v, h); !v.Valid() {
		app.failedValidationResponse(w, r, v.Errors)
		return
	}

	fmt.Fprintf(w, "%+v\n", input)
}

func (app *application) showHeartbeatHandler(w http.ResponseWriter, r *http.Request) {
	id, err := app.readIDParam(r)
	if err != nil {
		app.notFoundResponse(w, r)
		return
	}

	h := data.Heartbeat{
		ID:        id,
		CreatedAt: time.Now(),
		Heartbeat: 80,
	}

	err = app.writeJSON(w, http.StatusOK, envelope{"heartbeat": h}, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}

}
