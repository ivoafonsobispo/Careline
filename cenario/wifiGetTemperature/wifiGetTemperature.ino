#include "Arduino.h"
#include <ESP8266WiFi.h>
#include "OneWire.h"
#include "DallasTemperature.h"

class Firewall {
public:
  Firewall(); // Declarando o construtor
  int checkFirewall(String clientIP, String allowNet);
  // Outros membros e métodos...
};

// Implementação do construtor
Firewall::Firewall() {
  // Implementação do construtor
}

// Implementação do método checkFirewall
int Firewall::checkFirewall(String clientIP, String allowNet) {
  //Get Location(index) 
	int clientFirst = clientIP.indexOf(".", 0);
	int clientSecond = clientIP.indexOf(".", clientFirst + 1);
	int clientThird = clientIP.indexOf(".", clientSecond + 1);
	int clientFourth = clientIP.indexOf(".", clientThird + 1);
  
	int i=0;
	int subnet=0;

	//Count the occurances of . to figure out what we allow
 	while ( i <  allowNet.length() ) {
		if ( allowNet.substring(i,i+1) == "." ) {
			subnet++;
		}
		//Kill trailing .'s 
		if (i == allowNet.length()-1 and allowNet.substring(i,i+1) == "." ) {
			subnet--;
			allowNet=allowNet.substring(0,i);
	  	}
	  	i++;
	}

	//Split the client IP in to octets only keep what we need to match against FW rule
	String c1o = clientIP.substring(0, clientFirst); 
  	String clientSubnet = c1o;
	if ( subnet == 1 || subnet == 2 || subnet == 3) {
		String c2o = clientIP.substring(clientFirst + 1, clientSecond);
  		clientSubnet = clientSubnet + "." +  c2o;
  	}
	if ( subnet == 2 || subnet == 3) {
		String c3o = clientIP.substring(clientSecond + 1, clientThird);
  		clientSubnet = clientSubnet + "." +  c3o;
  	}
  	if ( subnet == 3) {
  		String c4o = clientIP.substring(clientThird + 1, clientFourth);
  		clientSubnet = clientSubnet + "." + c4o;
  	}
	//Troubleshooting
	Serial.println("Firewall Allow: " + allowNet);
	Serial.println("Client Subnet: " + clientSubnet);

	if ( clientSubnet != allowNet ) {
		//Troubleshooting
		//Serial.println("Blocking Client");
		return 1;
	} else {
		return 0;
	}
}


const char WiFiSSID[] = "labs"; //Enter SSID
const char WiFiPSK[] = "password"; //Enter Password

const int tempPin = A0;

//Set the subnet you will ALLOW traffic from. 
//Right now it just supports /24, /16 or /8 networks. 
//i.e. allowNet="192.168.1"  | allowNet="172.16" | allowNet="10" 
String allowNet = "10.20.229"; 

WiFiServer server(80);

//Instantiate the firewall
Firewall firewall;

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

float temp = 0;


IPAddress ip(10, 20, 229, 180);
// Set your Gateway IP address
IPAddress gateway(10, 20, 229, 254);

IPAddress subnet(255, 255, 254, 0);

IPAddress primaryDNS(8, 8, 8, 8);   //optional
IPAddress secondaryDNS(8, 8, 4, 4); //optional

// Variable to store the HTTP request
String header;

void setup()
{
  Serial.begin(115200);
  connectWiFi();
  server.begin();
}

void connectWiFi()
{
  //From Sparkfun sample: https://goo.gl/ubHfDF
  Serial.println("Connecting to: " + String(WiFiSSID));
  // Set WiFi mode to station (as opposed to AP or AP_STA)
  WiFi.mode(WIFI_STA);
  WiFi.begin(WiFiSSID, WiFiPSK);
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
  }
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop()
{
  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  //Check the firewall
  int firewallResponse = firewall.checkFirewall(client.remoteIP().toString(), allowNet);
  if (firewallResponse == 0) {
    Serial.println("New Client.");          // print a message out in the serial port
    String stringBuilder = "";
    String currentLine = "";                // make a String to hold incoming data from the client
    currentTime = millis();
    previousTime = currentTime;
    while (client.connected() && currentTime - previousTime <= timeoutTime) { // loop while the client's connected
      currentTime = millis();         
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        stringBuilder+= c;
        

        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          Serial.println("Builder");
          Serial.println(stringBuilder);
          if (currentLine.length() == 0) {
            buildRequest(stringBuilder, &client);
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
  }else {
    client.flush();
    // Send Reply
    buildUnauthorizedRequest(&client);
    client.stop();
  }
}

void buildRequest(String stringBuilder, WiFiClient *client) {
    char c = client->read();             // read a byte, then
    Serial.write(c);                    // print it out the serial monitor
    stringBuilder+= c;
    // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
    // and a content-type so the client knows what's coming, then a blank line:
    
    int control = 0;
    tempSensor.requestTemperaturesByIndex(0);
    if (stringBuilder.indexOf("/temperature") != -1) {
      buildOKRequest(client);
      client->print("{");
      client->print("\"value\":");
      //temp = tempSensor.requestTemperaturesByIndex(0);
      client->print(tempSensor.getTempCByIndex(0));
      client->print("}");
      control = 1;
    }
    if (stringBuilder.indexOf("/status") != -1) {
      buildOKRequest(client);
      client->print("{");
      if(tempSensor.getTempCByIndex(0) > temp){
        client->print("\"iotStatus\":\"incrementing\"");
      } 
      if(tempSensor.getTempCByIndex(0) < temp){
        client->print("\"iotStatus\":\"decrementing\"");
      } 
      if(tempSensor.getTempCByIndex(0) == temp) { 
          client->print("\"iotStatus\":\"Stopped\"");
      }
      temp = tempSensor.getTempCByIndex(0);
      
      client->print("}");
      control = 1;
    }
    if(control == 0){
      buildBADRequest(client);
    }
    client->println();
    // Break out of the while loop
   
}

void buildOKRequest(WiFiClient *client) {
  client->println("HTTP/1.1 200 OK");
  client->println("Access-Control-Allow-Origin: *");
  client->println("Content-type:application/json");
  client->println("Connection: close");
  client->println();
}

void buildBADRequest(WiFiClient *client) {
  client->println("HTTP/1.1 400 BAD-REQUEST");
  client->println("Content-type:application/json");
  client->println("Access-Control-Allow-Origin: *");
  client->println("Connection: close");
  client->println();
}

void buildUnauthorizedRequest(WiFiClient *client) {
  client->println("HTTP/1.1 403 UNAUTHORIZED");
  client->println("Content-type:application/json");
  client->println("Access-Control-Allow-Origin: *");
  client->println("Connection: close");
  client->println();
}
