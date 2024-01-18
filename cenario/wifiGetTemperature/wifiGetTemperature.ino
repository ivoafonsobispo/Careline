#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServerSecure.h>
#include <ESP8266mDNS.h>
#include <umm_malloc/umm_malloc.h>
#include <umm_malloc/umm_heap_select.h>
#include <ESP8266HTTPClient.h>
#include "Arduino.h"
#include "OneWire.h"
#include "DallasTemperature.h"

#ifndef STASSID
#define STASSID "labs"
#define STAPSK "PASSWORD"
#endif

class MyHTTPClient {
public:
  MyHTTPClient(); // Declarando o construtor
  void makeHttpPost(String type, String message);
  // Outros membros e métodos...
};

// Implementação do construtor
MyHTTPClient::MyHTTPClient() {
  // Implementação do construtor
}

void MyHTTPClient::makeHttpPost(String type, String message) {
  // Define o endereço do servidor para onde você deseja fazer a solicitação POST
  String serverAddress = "http://10.20.229.55/api/logs/iot";

  // Cria uma instância HTTPClient
  // Cria uma instância HTTPClient e um objeto WiFiClient
  HTTPClient http;
  WiFiClient wifiClient;

  // Inicia a comunicação com o servidor usando o WiFiClient
  http.begin(wifiClient, serverAddress);

  // Configura o cabeçalho da solicitação
  http.addHeader("Content-Type", "application/json");

  // Cria um objeto JSON com os dados desejados
  String jsonData = "{\"type\":\""+type+"\",\"message\":\""+message+"\"}";

  // Exibe os dados JSON no console para fins de depuração
  Serial.print("JSON Data: ");
  Serial.println(jsonData);

  // Realiza a solicitação POST com os dados fornecidos
  int httpResponseCode = http.POST(jsonData);

  // Verifica o código de resposta da solicitação
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
  } else {
    Serial.print("HTTP POST failed, error: ");
    Serial.println(httpResponseCode);
  }

  // Fecha a conexão
  http.end();
}


class Firewall {
public:
  Firewall(); // Declarando o construtor
  int checkFirewall(String clientIP, String allowNet);
  MyHTTPClient httpClient;
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
  httpClient.makeHttpPost("INFO","Firewall Allow: " + allowNet+"Client Subnet: " + clientSubnet);

	if ( clientSubnet != allowNet ) {
    httpClient.makeHttpPost("ERROR", "Firewall blocked client "+clientIP);
		return 1;
	} else {
    httpClient.makeHttpPost("INFO", "Firewall allowed client "+clientIP);
		return 0;
	}
}

const char* ssid = STASSID;
const char* password = STAPSK;

BearSSL::ESP8266WebServerSecure server(443);
BearSSL::ServerSessions serverCache(5);

#define USING_INSECURE_CERTS_AND_KEYS_AND_CAS 1
#include "ssl-tls-ca-key-cert-example.h"

String bigChunk;

Firewall firewall;
MyHTTPClient httpClient;

const int tempPin = A0;

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

//Set the subnet you will ALLOW traffic from. 
//Right now it just supports /24, /16 or /8 networks. 
//i.e. allowNet="192.168.1"  | allowNet="172.16" | allowNet="10" 
String allowNet = "10.20.228"; 

void handleRoot() {
  // Restante do seu código existente
  httpClient.makeHttpPost("INFO", "HTTP 200 OK");
  server.send(200, "text/plain", "Hello from esp8266 over HTTPS!");
}


void handleNotFound() {
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i = 0; i < server.args(); i++) { message += " " + server.argName(i) + ": " + server.arg(i) + "\n"; }
  httpClient.makeHttpPost("ERROR", "HTTP 404 Not Found");
  server.send(404, "text/plain", message);
}

void handleChunked() {
  server.chunkedResponseModeStart(200, F("text/html"));

  server.sendContent(bigChunk);
  server.sendContent(F("chunk 2"));
  server.sendContent(bigChunk);

  server.chunkedResponseFinalize();
}

void setup(void) {
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  Serial.println("");

  if (!WiFi.config(ip, gateway, subnet, primaryDNS, secondaryDNS)) {
    Serial.println("STA Failed to configure");
  }

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  configTime(3 * 3600, 0, "pool.ntp.org", "time.nist.gov");

  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (MDNS.begin("esp8266")) { Serial.println("MDNS responder started"); }

  server.getServer().setRSACert(new BearSSL::X509List(server_cert), new BearSSL::PrivateKey(server_private_key));

  // Cache SSL sessions to accelerate the TLS handshake.
  server.getServer().setCache(&serverCache);

  server.on("/", handleRoot);

  server.on("/temperature", []() {
    int firewallResponse = firewall.checkFirewall(server.client().remoteIP().toString(), allowNet);
    if (firewallResponse == 0) {
      httpClient.makeHttpPost("INFO", "A new client arrived in temperature route");
      tempSensor.requestTemperaturesByIndex(0);
      temp = tempSensor.getTempCByIndex(0);
      httpClient.makeHttpPost("INFO", "200, application/json, {value:"+String(temp)+"}");
      server.send(200, "application/json", "{\"value\":"+String(temp)+"}");
      return;
    }
    handleNotFound();

  });

  server.on("/status", []() {
    int firewallResponse = firewall.checkFirewall(server.client().remoteIP().toString(), allowNet);
    if (firewallResponse == 0) {
      httpClient.makeHttpPost("INFO", "A new client arrived in /status route");
      if(tempSensor.getTempCByIndex(0) > temp){
        temp = tempSensor.getTempCByIndex(0);
        httpClient.makeHttpPost("INFO", "200, application/json, {iotStatus:incrementing}");
        server.send(200, "application/json", "{\"iotStatus\":\"incrementing\"}");
        return;
      } 
      if(tempSensor.getTempCByIndex(0) < temp){
        temp = tempSensor.getTempCByIndex(0);
        httpClient.makeHttpPost("INFO", "200, application/json, {iotStatus:decrementing}");
        server.send(200, "application/json", "{\"iotStatus\":\"decrementing\"}");
        return;
      } 
      if(tempSensor.getTempCByIndex(0) == temp) { 
        temp = tempSensor.getTempCByIndex(0);
        httpClient.makeHttpPost("INFO", "200, application/json, {iotStatus:Stopped}");
        server.send(200, "application/json", "{\"iotStatus\":\"Stopped\"}");
        return;
      }
      handleNotFound();
    }
  });

  server.on("/chunks", handleChunked);

  server.onNotFound(handleNotFound);

  // prepare chunk in ram for sending
  constexpr int chunkLen = 4000;  // ~4KB chunk
  bigChunk.reserve(chunkLen);
  bigChunk = F("chunk of len ");
  bigChunk += chunkLen;
  String piece = F("-blah");
  while (bigChunk.length() < chunkLen - piece.length())
    bigChunk += piece;

  server.begin();
  Serial.println("HTTPS server started");
}

extern "C" void stack_thunk_dump_stack();

void processKey(Print& out, int hotKey) {
  switch (hotKey) {
    case 'd':
      {
        HeapSelectDram ephemeral;
        umm_info(NULL, true);
        break;
      }
    case 'i':
      {
        HeapSelectIram ephemeral;
        umm_info(NULL, true);
        break;
      }
    case 'h':
      {
        {
          HeapSelectIram ephemeral;
          Serial.printf(PSTR("IRAM ESP.getFreeHeap:  %u\n"), ESP.getFreeHeap());
        }
        {
          HeapSelectDram ephemeral;
          Serial.printf(PSTR("DRAM ESP.getFreeHeap:  %u\n"), ESP.getFreeHeap());
        }
        break;
      }
#ifdef DEBUG_ESP_PORT
    // From this context stack_thunk_dump_stack() will only work when Serial
    // debug is enabled.
    case 'p':
      out.println(F("Calling stack_thunk_dump_stack();"));
      stack_thunk_dump_stack();
      break;
#endif
    case 'R':
      out.printf_P(PSTR("Restart, ESP.restart(); ...\r\n"));
      ESP.restart();
      break;
    case '\r': out.println();
    case '\n': break;
    case '?':
      out.println();
      out.println(F("Press a key + <enter>"));
      out.println(F("  h    - Free Heap Report;"));
      out.println(F("  i    - iRAM umm_info(null, true);"));
      out.println(F("  d    - dRAM umm_info(null, true);"));
#ifdef DEBUG_ESP_PORT
      out.println(F("  p    - call stack_thunk_dump_stack();"));
#endif
      out.println(F("  R    - Restart, ESP.restart();"));
      out.println(F("  ?    - Print Help"));
      out.println();
      break;
    default:
      out.printf_P(PSTR("\"%c\" - Not an option?  / ? - help"), hotKey);
      out.println();
      processKey(out, '?');
      break;
  }
}


void loop(void) {
  server.handleClient();
  MDNS.update();
  if (Serial.available() > 0) {
    int hotKey = Serial.read();
    processKey(Serial, hotKey);
  }
}
