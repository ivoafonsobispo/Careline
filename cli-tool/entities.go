package main

import "encoding/json"

type Patient struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Password string `json:"password"`
	NUS      string `json:"nus"`
}

type Professionals struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Password string `json:"password"`
	NUS      string `json:"nus"`
}

type Heartbeats struct {
	Heartbeat int
	Patient   Patient
}

type Temperature struct {
	Temperature int
	Patient     Patient
}

func generatePatient() (string, error) {
	patient := Patient{
		Name:     "Ivo Bispo",
		Email:    "ivo@mail.pt",
		Password: "pa55word",
		NUS:      "123456789",
	}

	jsonData, err := json.Marshal(patient)
	if err != nil {
		return "", err
	}

	return string(jsonData), nil
}
