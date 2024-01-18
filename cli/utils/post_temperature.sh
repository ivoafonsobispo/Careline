#!/bin/bash

# Set variables
api_url="http://localhost:8080/api/patients/1/temperatures"
bearer_token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJpYXQiOjE3MDUwNzA4MzYsImV4cCI6MTcwNTA3NDQzNn0.yV4DAO96TphRTWR0p7NUmMZb73mZvoRfoNlOcWruwEs"

# Loop for 20 POST requests with random temperature values ranging from 35 to 45
for ((i = 0; i < 20; i++)); do
    random_temperature=$((RANDOM % (45 - 35 + 1) + 35))
    json_body="{\"temperature\":$random_temperature}"
    
    # Make POST request using curl
    curl -X POST -H "Authorization: Bearer $bearer_token" -H "Content-Type: application/json" -d "$json_body" "$api_url"
done

