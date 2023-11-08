package main

import (
	"net/http"
	"time"
)

type week struct {
	Day     int
	Weekday string
}

func (app *application) previousWeek(w http.ResponseWriter, r *http.Request) {
	env := envelope{
		"0": week{
			Day:     time.Now().Day(),
			Weekday: time.Now().Weekday().String(),
		},
		"1": week{
			Day:     time.Now().Day() - 1,
			Weekday: time.Now().Add(-24 * time.Hour).Weekday().String(),
		},
		"2": week{
			Day:     time.Now().Day() - 2,
			Weekday: time.Now().Add(-48 * time.Hour).Weekday().String(),
		},
		"3": week{
			Day:     time.Now().Day() - 3,
			Weekday: time.Now().Add(-72 * time.Hour).Weekday().String(),
		},
		"4": week{
			Day:     time.Now().Day() - 4,
			Weekday: time.Now().Add(-96 * time.Hour).Weekday().String(),
		},
		"5": week{
			Day:     time.Now().Day() - 5,
			Weekday: time.Now().Add(-120 * time.Hour).Weekday().String(),
		},
		"6": week{
			Day:     time.Now().Day() - 6,
			Weekday: time.Now().Add(-144 * time.Hour).Weekday().String(),
		},
	}

	err := app.writeJSON(w, http.StatusOK, env, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}
