#!/bin/bash

# Function to send multiple POST requests
send_post_requests() {
  local api_url="$1"
  local post_data="$2"

  echo "Sending POST request to $api_url"
  curl -X POST -H "Content-Type: application/json" -d "$post_data" "$api_url"
  echo -e "\n"  # Add a newline for better readability
}

# Check if the required parameters are provided
if [ $# -lt 1 ]; then
  echo "Usage: $0 <API_URL>" 
  exit 1
fi

# Get command line arguments
API_URL="$1"

POST_PATIENT_1='{"name": "Alice Johnson","email": "alice@mail.com","password": "secure123","nus": "987654321"}'
POST_PATIENT_2='{"name": "Bob Smith","email": "bob@mail.net","password": "secret456","nus": "111222333"}'
POST_PATIENT_3='{"name": "Eva Martinez","email": "eva@mail.org","password": "confidential789","nus": "555666777"}'

POST_PROFESSIONAL_1='{"name": "Dra. Alice Johnson","email": "dr.alice@mail.com","password": "secure123","nus": "123456789"}'
POST_PROFESSIONAL_2='{"name": "Dr. Bob Smith","email": "dr.bob@mail.net","password": "secret456","nus": "222333444"}'
POST_PROFESSIONAL_3='{"name": "Dra. Eva Martinez","email": "dr.eva@mail.org","password": "confidential789","nus": "777888999"}'

POST_HEARTBEAT_1='{"heartbeat":"69"}'
POST_HEARTBEAT_2='{"heartbeat":"81"}'
POST_HEARTBEAT_3='{"heartbeat":"126"}'
POST_HEARTBEAT_4='{"heartbeat":"65"}'
POST_HEARTBEAT_5='{"heartbeat":"94"}'
POST_HEARTBEAT_6='{"heartbeat":"136"}'
POST_HEARTBEAT_7='{"heartbeat":"71"}'
POST_HEARTBEAT_8='{"heartbeat":"98"}'

POST_TEMPERATURE_1='{"temperature":"36.3"}'
POST_TEMPERATURE_2='{"temperature":"37.5"}'
POST_TEMPERATURE_3='{"temperature":"36.1"}'
POST_TEMPERATURE_4='{"temperature":"38.7"}'
POST_TEMPERATURE_5='{"temperature":"39.9"}'
POST_TEMPERATURE_6='{"temperature":"36.0"}'
POST_TEMPERATURE_7='{"temperature":"37"}'
POST_TEMPERATURE_8='{"temperature":"36.9"}'

POST_DIAGNOSIS_1='{"diagnosis": "This is your diagnosis, Alice","prescriptions": ["Med 1","Med 2"]}'
POST_DIAGNOSIS_2='{"diagnosis": "This is your diagnosis, Bob","prescriptions": ["Med 1","Med 2"]}'
POST_DIAGNOSIS_3='{"diagnosis": "This is your diagnosis, Eva","prescriptions": ["Med 1","Med 2"]}'
POST_DIAGNOSIS_4='{"diagnosis": "This is your diagnosis, Eva","prescriptions": ["Med 1","Med 2"]}'
POST_DIAGNOSIS_5='{"diagnosis": "This is your diagnosis, Bob","prescriptions": ["Med 1","Med 2"]}'

# Call the function with user-provided parameters
send_post_requests "$API_URL/patients" "$POST_PATIENT_1"
send_post_requests "$API_URL/patients" "$POST_PATIENT_2"
send_post_requests "$API_URL/patients" "$POST_PATIENT_3"
send_post_requests "$API_URL/professionals" "$POST_PROFESSIONAL_1"
send_post_requests "$API_URL/professionals" "$POST_PROFESSIONAL_2"
send_post_requests "$API_URL/professionals" "$POST_PROFESSIONAL_3"
send_post_requests "$API_URL/patients/1/heartbeats" "$POST_HEARTBEAT_1"
send_post_requests "$API_URL/patients/1/heartbeats" "$POST_HEARTBEAT_2"
send_post_requests "$API_URL/patients/1/heartbeats" "$POST_HEARTBEAT_3"
send_post_requests "$API_URL/patients/2/heartbeats" "$POST_HEARTBEAT_4"
send_post_requests "$API_URL/patients/2/heartbeats" "$POST_HEARTBEAT_5"
send_post_requests "$API_URL/patients/2/heartbeats" "$POST_HEARTBEAT_6"
send_post_requests "$API_URL/patients/3/heartbeats" "$POST_HEARTBEAT_7"
send_post_requests "$API_URL/patients/3/heartbeats" "$POST_HEARTBEAT_8"
send_post_requests "$API_URL/patients/1/temperatures" "$POST_TEMPERATURE_1"
send_post_requests "$API_URL/patients/1/temperatures" "$POST_TEMPERATURE_2"
send_post_requests "$API_URL/patients/1/temperatures" "$POST_TEMPERATURE_3"
send_post_requests "$API_URL/patients/2/temperatures" "$POST_TEMPERATURE_4"
send_post_requests "$API_URL/patients/2/temperatures" "$POST_TEMPERATURE_5"
send_post_requests "$API_URL/patients/2/temperatures" "$POST_TEMPERATURE_6"
send_post_requests "$API_URL/patients/3/temperatures" "$POST_TEMPERATURE_7"
send_post_requests "$API_URL/patients/3/temperatures" "$POST_TEMPERATURE_8"
send_post_requests "$API_URL/professionals/1/patients/1/diagnosis" "$POST_DIAGNOSIS_1"
send_post_requests "$API_URL/professionals/1/patients/2/diagnosis" "$POST_DIAGNOSIS_2"
send_post_requests "$API_URL/professionals/1/patients/3/diagnosis" "$POST_DIAGNOSIS_3"
send_post_requests "$API_URL/professionals/2/patients/1/diagnosis" "$POST_DIAGNOSIS_4"
send_post_requests "$API_URL/professionals/2/patients/2/diagnosis" "$POST_DIAGNOSIS_5"
