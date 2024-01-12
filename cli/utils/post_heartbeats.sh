#!/bin/bash

# Set variables
api_url="http://localhost:8080/api/patients/1/heartbeats"
bearer_token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJpYXQiOjE3MDUwNzA4MzYsImV4cCI6MTcwNTA3NDQzNn0.yV4DAO96TphRTWR0p7NUmMZb73mZvoRfoNlOcWruwEs"

# Loop for 20 POST requests with random heartbeat values ranging from 60 to 140
for ((i = 0; i < 200; i++)); do
    random_heartbeat=$((RANDOM % (140 - 60 + 1) + 60))
    json_body="{\"heartbeat\":$random_heartbeat}"
    
    # Make POST request using curl
    curl -X POST -H "Authorization: Bearer $bearer_token" -H "Content-Type: application/json" -d "$json_body" "$api_url"
done

