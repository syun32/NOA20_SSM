#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <string.h>
#include <SPI.h>//spi 함수들을 사용하기위한 라이브러리
#include <SoftwareSerial.h>//nodemcu의 다른 디지털핀을 직렬통신을 할 수 있게 해주는 라이브러리

SoftwareSerial mySerial(D5, D6); //소프트시리얼 객체선언(d5(수신=rx),d6(전송=tx)) nodemcu용

const char* ssid = "DaeWoo";
const char* password = "74187418";
char serverr[] = "192.168.0.16";// 공유기(ap), ssid,비밀번호 지정 필요


WiFiClient client;
WiFiServer server(80);  // http통신은 80번포트사용
HTTPClient http;


String phpHost;
int httpCode;
int LED_pin = D1;


void setup() {
  mySerial.begin(115200);
  Serial.begin(115200);
  SPI.setClockDivider(SPI_CLOCK_DIV8);//spi통신속도를 설정을 위해 클럭 나누기 값 설정

  WiFi.begin(ssid, password);//와이파이에 연결하는 함수
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }//와이파이에 연결이 될때 까지 대기
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());//아이피 주소를 출력
}

void loop() {

  String str = ""; //str 아두이노로 보낼 값을 저장
  if (client.connect(serverr, 9090)) {  //서버에연결하기
    Serial.println("\n\nConnecting...");
    client.print(F("GET /NOA_ICT/inoExecuteData.php?id=0 HTTP/1.1\r\n"));  // http요청을보냄
    // client.print(F("Host: 172.30.1.37\r\n"));
    client.print(F("Host: 192.168.0.16\r\n"));
    client.print(F("Connection: close\r\n"));
    client.print(F("\r\n\r\n"));
    delay(1000);
    if (client.available()) {
      while (client.available()) {
        char c = client.read();//서버로부터 받아오는 문자값
        if (c == '*') {
          while (client.available()) {
            char c = client.read();//서버로부터 받아오는 문자값
            Serial.println(c);//nodemcu시리얼창에 받아온값 표시
            SPI.transfer(c);//spi 통신에서 데이터를 보내고 받는 함수
            str += c; //받아온 c값들을 str으로 묶어서 표시
            Serial.println(str);//str값을 nodemcu상에 표시
          }
        }
      }
    }
    mySerial.println(str);//아두이노쪽으로 서버로부터 받아온 str을 송신
  }
}
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <string.h>
#include <SPI.h>//spi 함수들을 사용하기위한 라이브러리
#include <SoftwareSerial.h>//nodemcu의 다른 디지털핀을 직렬통신을 할 수 있게 해주는 라이브러리

SoftwareSerial mySerial(D5, D6); //소프트시리얼 객체선언(d5(수신=rx),d6(전송=tx)) nodemcu용

const char* ssid = "DaeWoo";
const char* password = "74187418";
char serverr[] = "192.168.0.16";// 공유기(ap), ssid,비밀번호 지정 필요


WiFiClient client;
WiFiServer server(80);  // http통신은 80번포트사용
HTTPClient http;


String phpHost;
int httpCode;
int LED_pin = D1;


void setup() {
  mySerial.begin(115200);
  Serial.begin(115200);
  SPI.setClockDivider(SPI_CLOCK_DIV8);//spi통신속도를 설정을 위해 클럭 나누기 값 설정

  WiFi.begin(ssid, password);//와이파이에 연결하는 함수
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }//와이파이에 연결이 될때 까지 대기
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());//아이피 주소를 출력
}

void loop() {

  String str = ""; //str 아두이노로 보낼 값을 저장
  if (client.connect(serverr, 9090)) {  //서버에연결하기
    Serial.println("\n\nConnecting...");
    client.print(F("GET /NOA_ICT/inoExecuteData.php?id=0 HTTP/1.1\r\n"));  // http요청을보냄
    // client.print(F("Host: 172.30.1.37\r\n"));
    client.print(F("Host: 192.168.0.16\r\n"));
    client.print(F("Connection: close\r\n"));
    client.print(F("\r\n\r\n"));
    delay(1000);
    if (client.available()) {
      while (client.available()) {
        char c = client.read();//서버로부터 받아오는 문자값
        if (c == '*') {
          while (client.available()) {
            char c = client.read();//서버로부터 받아오는 문자값
            Serial.println(c);//nodemcu시리얼창에 받아온값 표시
            SPI.transfer(c);//spi 통신에서 데이터를 보내고 받는 함수
            str += c; //받아온 c값들을 str으로 묶어서 표시
            Serial.println(str);//str값을 nodemcu상에 표시
          }
        }
      }
    }
    mySerial.println(str);//아두이노쪽으로 서버로부터 받아온 str을 송신
  }
}
