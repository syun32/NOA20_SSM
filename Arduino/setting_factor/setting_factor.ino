#include "HX711.h"
#define DOUT  3// 데이터핀을 3번핀으로 지정
#define CLK  2//클럭핀을 2번 핀으로 지정
HX711 scale(DOUT, CLK);//scale 객체 생성
float calibration_factor = 1309050; // 눈금의 비율(calibration_factor)의 초기값을 임의로 설정
void setup()
{
  Serial.begin(115200); // 아두이노 통신 속도 설정 115200bps
  scale.tare(); //현재 무게값을 0으로 맞춤
}

void loop()
{
  scale.set_scale(calibration_factor); // 임의로 설정한 calibration factor 값을 적용
  int weight = scale.get_units() * 453.592; ; // 로드 셀로 측정한 무게 값(단위 [lb])을 gram 단위로 변환
  Serial.print("Reading: ");
  delay(200);
  Serial.print(weight,1);
  Serial.print(" g");
  Serial.print(" calibration_factor: ");
  Serial.print(calibration_factor);
  Serial.println();
  if (Serial.available())
  {
    int standard = Serial.parseInt(); // 무게를 알고 있는 물체의 실제 무게
    Serial.print("standard : ");
    Serial.print(standard);

    while (standard != weight)// 로드셀로 측정한 무게(weight)와 실제 무게(standard)가 같을 때 까지 반복
    {
      scale.set_scale(calibration_factor);
      weight = scale.get_units() * 453.592;
      if (standard < weight)
        calibration_factor += 1000;//측정 무게가 실제 무게보다 클 때 calibration_factor값이 1000씩 증가
      if (standard > weight)
        calibration_factor -= 1000;//측정 무게가 실제 무게보다 작을 때 calibration_factor값이 1000씩 감소
      Serial.print("Reading: ");
      delay(200);
      Serial.print(weight,1);
      Serial.print(" g");
      Serial.print("standard : ");
      Serial.print(standard);
      Serial.print(" calibration_factor: ");
      Serial.print(calibration_factor);
      Serial.println();
    }
    Serial.print("calibration_factor 조정 완료");
    Serial.print(" calibration_factor: ");
    Serial.print(calibration_factor);
  }
}
