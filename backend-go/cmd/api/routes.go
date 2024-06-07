package main

import (
	"net/http"

	"github.com/julienschmidt/httprouter"
)

func (app *application) routes() http.Handler {
	router := httprouter.New()

	router.NotFound = http.HandlerFunc(app.notFoundResponse)
	router.MethodNotAllowed = http.HandlerFunc(app.methodNotAllowed)

	router.HandlerFunc(http.MethodGet, "/v1/healthcheck", app.healthcheckHandler)
	router.HandlerFunc(http.MethodGet, "/v1/previousweek", app.previousWeek)

	router.HandlerFunc(http.MethodGet, "/v1/heartbeat", app.listHeartbeatsHandler)
	router.HandlerFunc(http.MethodPost, "/v1/heartbeat", app.createHeartbeatHandler)
	router.HandlerFunc(http.MethodGet, "/v1/heartbeat/:id", app.showHeartbeatHandler)
	router.HandlerFunc(http.MethodPut, "/v1/heartbeat/:id", app.updateHeartbeatHandler)
	router.HandlerFunc(http.MethodDelete, "/v1/heartbeat/:id", app.deleteHeartbeatHandler)

	router.HandlerFunc(http.MethodGet, "/v1/bodytemperature", app.listBodytemperatureHandler)
	router.HandlerFunc(http.MethodPost, "/v1/bodytemperature", app.createBodytemperatureHandler)
	router.HandlerFunc(http.MethodGet, "/v1/bodytemperature/:id", app.showBodytemperatureHandler)
	router.HandlerFunc(http.MethodPut, "/v1/bodytemperature/:id", app.updateBodytemperatureHandler)
	router.HandlerFunc(http.MethodDelete, "/v1/bodytemperature/:id", app.deleteBodytemperatureHandler)

	router.HandlerFunc(http.MethodPost, "/v1/patients", app.registerPatientHandler)
	router.HandlerFunc(http.MethodPost, "/v1/professionals", app.registerProfessionalHandler)

	return app.recoverPanic(app.rateLimit(router))
}
