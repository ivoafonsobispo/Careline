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
			Weekday: time.Now().AddDate(0, 0, -1).Weekday().String(),
		},
		"2": week{
			Day:     time.Now().Day() - 2,
			Weekday: time.Now().AddDate(0, 0, -2).Weekday().String(),
		},
		"3": week{
			Day:     time.Now().Day() - 3,
			Weekday: time.Now().AddDate(0, 0, -3).Weekday().String(),
		},
		"4": week{
			Day:     time.Now().Day() - 4,
			Weekday: time.Now().AddDate(0, 0, -4).Weekday().String(),
		},
		"5": week{
			Day:     time.Now().Day() - 5,
			Weekday: time.Now().AddDate(0, 0, -5).Weekday().String(),
		},
		"6": week{
			Day:     time.Now().Day() - 6,
			Weekday: time.Now().AddDate(0, 0, -6).Weekday().String(),
		},
	}

	err := app.writeJSON(w, http.StatusOK, env, nil)
	if err != nil {
		app.serverErrorResponse(w, r, err)
	}
}
