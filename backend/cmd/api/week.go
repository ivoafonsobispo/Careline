package main

import (
	"net/http"
	"time"
)

func (app *application) previousWeek(w http.ResponseWriter, r *http.Request) {
	env := envelope{
		time.Now().Weekday().String():                       time.Now().Day(),
		time.Now().Add(-24 * time.Hour).Weekday().String():  time.Now().Day() - 1,
		time.Now().Add(-48 * time.Hour).Weekday().String():  time.Now().Day() - 2,
		time.Now().Add(-72 * time.Hour).Weekday().String():  time.Now().Day() - 3,
		time.Now().Add(-96 * time.Hour).Weekday().String():  time.Now().Day() - 4,
		time.Now().Add(-120 * time.Hour).Weekday().String(): time.Now().Day() - 5,
		time.Now().Add(-144 * time.Hour).Weekday().String(): time.Now().Day() - 6,
	}

	err := app.writeJSON(w, http.StatusOK, env, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}
