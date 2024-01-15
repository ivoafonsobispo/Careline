#!/bin/bash

# Set variables
api_url="http://10.20.229.55/api/patients/1/heartbeats"
bearer_token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJpYXQiOjE3MDQ5ODczNzEsImV4cCI6MTcwNDk5MDk3MX0.m5Eb5OUla4bNaD7Yl2ZXYKjjOTj6WcTT8sgQlFD8rkk"

# Loop for 20 POST requests with random heartbeat values ranging from 60 to 140
for ((i = 0; i < 20; i++)); do
    random_heartbeat=$((RANDOM % (140 - 60 + 1) + 60))
    json_body="{\"heartbeat\":$random_heartbeat}"
    
    # Make POST request using curl
    curl -X POST -H "Authorization: Bearer $bearer_token" -H "Content-Type: application/json" -d "$json_body" "$api_url"
done

