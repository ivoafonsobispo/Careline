package main

import (
	"fmt"
	"os"
)

var entities = []string{"patients", "professionals", "heartbeats", "temperatures"}

var methods = []string{"GET", "POST", "PUT", "DELETE"}

func main() {
	if len(os.Args) < 3 {
		fmt.Println("Usage: go run main.go <method> <entity>")
		return
	}

	method := os.Args[1]
	entity := os.Args[2]

	if !contains(methods, method) {
		fmt.Println("Method not found")
		return
	}

	if !contains(entities, entity) {
		fmt.Println("Entity not found")
		return
	}

	fmt.Println("Method:", method)
	fmt.Println("Entity:", entity)

	switch method {
	case "POST":
		switch entity {
		case "patients":
			body, err := generatePatient()
			if err != nil {
				fmt.Println("Error generating patient data:", err)
				return
			}
			post("http://localhost:8080/api/patients", body)
		}
	case "GET":
		switch entity {
		case "patients":
			get("http://localhost:8080/api/patients")
		}
	}
}
