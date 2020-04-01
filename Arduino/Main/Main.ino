#include "HX711.h"

#include <SoftwareSerial.h>

SoftwareSerial mySerial(13, 12);
#define DOUT  3
#define CLK  2
int operation = 0; // 모터의 작동을 제어하기 위한 변수(1:작동, 0:멈춤)
int downmotor1 = 6; // 모터b(아래쪽 조미료 분출) 6번 포트로 제어(OUT1)
int downmotor2 = 5; // 모터b(아래쪽 조미료 분출) 5번 포트로 제어(OUT2)
int uppermotor1 = 10; // 모터a(위쪽 조미료 뭉침 방지) 10번 포트로 제어(OUT1)
int uppermotot2 = 11; // 모터a(위쪽 조미료 뭉침 방지) 11번 포트로 제어(OUT2)
HX711 scale(DOUT, CLK);//scale 객체 생성
long target; // 조미료를 분출 해야하는 목표 무게 변수 선언
float calibration_factor = 1296050; // 제작한 모델에 맞는 눈금의 비율(calibration_factor)을 설정(이 모델에서는 1309050일 때 실제와 동일)

void setup() {
  pinMode(downmotor1, OUTPUT);
  pinMode(downmotor2, OUTPUT);// 모터 세팅
  pinMode(8, INPUT);// 버튼세팅
  mySerial.begin(115200);
  Serial.begin(115200); // 아두이노 통신 속도 설정 115200bps
  Serial.println("무게를 입력하세요");
  scale.set_scale();
  scale.tare(); //현재 무게값을 0으로 맞춤
}

void loop()
{
  scale.set_scale(calibration_factor); // 설정한 calibration factor 값을 적용
  int weight = scale.get_units() * 453.592;// 로드 셀로 측정한 무게 값(단위 [lb])을 gram 단위로 변환

  if (Serial.available() || mySerial.available())
  {
    target = mySerial.parseInt();//node mcu에서 전달되는 목표 무게
    Serial.print("target : ");
    Serial.println(target);
    Serial.print("reading : ");
    Serial.print(weight);
    Serial.println(" g");
  }
  if (target == 0) return;; //node mcu로부터 받은 값이 0이면 값을 반환
  int rest = 9999; // 조미료를 분출 해야하는 남은 무게에 대한 변수의 초기값
  while (rest > 0) // 분출한 조미료 무게가 목표무게에 도달하면 while구문을 벗어남
  {
    rest = target - weight; // 분출해야 하는 남은 무게
    weight = scale.get_units() * 453.592;
    Serial.print("Reading: ");
    delay(200);
    Serial.print(weight);
    Serial.print(" g");
    Serial.print("target : ");
    Serial.print(target);
    Serial.print(" calibration_factor: ");
    Serial.print(calibration_factor);
    Serial.println();

    if (digitalRead(8) == HIGH)// 버튼을 누르면 모터a,모터b가 동작
    {
      operation = 1;
      scale.tare();
    }
    while (weight < target && operation == 1) {

      analogWrite(downmotor1, 200); //모터b 시계 방향 작동
      analogWrite(downmotor2, 0); //모터b 시계 방향으로 작동시키기 위해 반시계 방향을 0으로설정
      analogWrite(uppermotor1, 200); //모터a 시계 방향 작동
      analogWrite(uppermotot2, 0); //모터a 시계 방향으로 작동시키기 위해 반시계 방향을 0으로설정

      if (weight >= target * 0.5) { // 분출한 양이 목표무게의 절반을 넘어가면 일정 간격으로 모터가 멈춤
        delay(50);
        analogWrite(downmotor1, 0);
        analogWrite(downmotor2, 0);
        analogWrite(uppermotor1, 0);
        analogWrite(uppermotot2, 0); // 모터a, 모터b가 모두 멈춤
      }
      weight = scale.get_units() * 453.592;
      Serial.print(weight);
      Serial.println(" g");
      delay(100);
    }
    if (weight >= target)// 추출한 조미료 무게가 목표 무게를 넘어가면 작동 중지
    {
      operation = 0;
      analogWrite(downmotor1, 0);
      analogWrite(downmotor2, 0);
      analogWrite(uppermotor1, 0);
      analogWrite(uppermotot2, 0); // 모터a, 모터b가 모두 멈춤
      delay(1000);
    }
  }
}
