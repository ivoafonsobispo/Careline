#include "ESP8266WiFi.h"
#include "OneWire.h"
#include "DallasTemperature.h"

const char* ssid = "labs"; //Enter SSID
const char* password = "1nv3nt@r2023_IPLEIRIA"; //Enter Password


// Set web server port number to 80
WiFiServer server(80);


// Variable to store the HTTP request
String header;

// Auxiliar variables to store the current output state
String output5State = "off";
String output4State = "off";

// Assign output variables to GPIO pins
const int output5 = 5;
const int output4 = 4;

// Current time
unsigned long currentTime = millis();
// Previous time
unsigned long previousTime = 0; 
// Define timeout time in milliseconds (example: 2000ms = 2s)
const long timeoutTime = 2000;

OneWire oneWire(D4);
DallasTemperature tempSensor(&oneWire);




void setup(void)
{ 
  Serial.begin(115200);
  // Connect to WiFi
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) 
  {
     delay(500);
     Serial.print("*");
  }
  
  Serial.println("");
  Serial.println("WiFi connection Successful");
  Serial.print("The IP Address of ESP8266 Module is: ");
  Serial.print(WiFi.localIP());// Print the IP address
  server.begin();
  tempSensor.begin();

}

void loop() 
{
  WiFiClient client = server.available();   // Listen for incoming clients
  //Serial.println(client);
  if (client) {                             // If a new client connects,
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    currentTime = millis();
    previousTime = currentTime;
    while (client.connected() && currentTime - previousTime <= timeoutTime) { // loop while the client's connected
      currentTime = millis();         
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:application/json");
            client.println("Connection: close");
            client.println();

            tempSensor.requestTemperaturesByIndex(0);
            Serial.print("Temperature: ");
            Serial.print(tempSensor.getTempCByIndex(0));
            Serial.println(" C");
                      
            // Display the HTML web page
            client.println("{");
            client.print("\"data\":");
            client.print(tempSensor.getTempCByIndex(0));
            client.println(",");
            client.println("}");
            
            client.println();
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    client.stop();
    Serial.println("Client disconnected.");
    Serial.println("");
}
}



//TODO execute post https://randomnerdtutorials.com/esp8266-nodemcu-http-get-post-arduino/
