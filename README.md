# NOA20_ICT

NOA 20 | 씀(SSM, Smart Spice Machine)  
스마트 조미료통  
2020 ICT 융합 프로젝트 공모전 장려상🏅

## 설명

사용자가 어플리케이션을 통해 레시피를 입력하면 레시피에 사용되는 조미료를 서버에 있는 DB에 저장한다.  
레시피를 실행하면 서버에 있는 데이터를 조미료통으로 전송한다.  
데이터를 받게 되면 조미료통에서 그 수치만큼 조미료가 분출된다.

## 기능

- 사용자가 자주 사용하는 레시피 저장 및 관리
- 레시피에 저장하지 않고 '바로 실행' 가능
- 조미료통과 통신
- PHP를 통해 MySQL과 연동하여 사용자 데이터베이스 생성
- 사용자 데이터 시각화

## [Android](https://github.com/eun-seong/NOA20_SSM/tree/master/NOA_ICT)

안드로이드 어플리케이션

#### InitialActivity.java

사용자가 조미료통에 어떤 조미료를 넣을지 설정하는 액티비티

```java
PreferenceManager.setString(mContext, "condiment0", condiment_[0]);
PreferenceManager.setString(mContext, "condiment1", condiment_[1]);
```

#### MainActivity.java

사용자가 설정한 레시피를 서버에서 불러와 리스트뷰를 통해 보여주는 액티비티
레시피를 실행하면 조미료통으로 값을 전달한다.
주요 코드

```java
String myJSON = new GetData().execute("getData.php").get();

JSONObject jsonObject = new JSONObject(myJSON);
JSONArray dataJSON = jsonObject.getJSONArray(getString(R.string.DB_tableName));

int num = dataJSON.length();
for (int I = 0; I < num; I++) {
    Log.d(TAG, "updateLV: for loop number : " + i);
    JSONObject c = dataJSON.getJSONObject(i);
    int id = c.getInt(getString(R.string.DB_colID));
    int[] gram = {Integer.parseInt(c.getString(getString(R.string.DB_colCondiment0))), Integer.parseInt(c.getString(getString(R.string.DB_colCondiment1)))};
    Data data = new Data();

    data.setId(id);
    data.setTitle(c.getString(getString(R.string.DB_colTitle)));
    data.setCondiment0(condiment[0]);
    data.setCondiment1(condiment[1]);
    data.setGram0(gram[0]);
    data.setGram1(gram[1]);

    list.add(data);
}
```

#### PlayActivity.java

레시피에 저장하지 않고 실시간으로 실행하는 액티비티

```java
String gram0 = gram[0].getText().toString();
String gram1 = gram[1].getText().toString();

InsertData task = new InsertData();
task.execute("executeData.php",
        getString(R.string.DB_colCondiment0), gram0,
        getString(R.string.DB_colCondiment1), gram1
);
```

## Server

### MySQL

서버에 존재하는 데이터베이스

#### TABLE | usr_statistics

사용자가 저장한 모든 레시피가 저장되어 있다.  
각 열은 레시피의 아이디, 레시피 이름, 조미료1, 조미료2를 의미한다.

```sql
mysql> desc recipe;
+------------+----------+------+-----+---------+----------------+
| Field      | Type     | Null | Key | Default | Extra          |
+------------+----------+------+-----+---------+----------------+
| id         | int      | NO   | PRI | NULL    | auto_increment |
| title      | char(30) | YES  |     | NULL    |                |
| condiment0 | int      | YES  |     | NULL    |                |
| condiment1 | int      | YES  |     | NULL    |                |
+------------+----------+------+-----+---------+----------------+
```

#### TABLE | ino_data

아두이노로 보낼 데이터가 담겨 있다.  
각 열은 조미료통의 아이디, 실행할 조미료의 그램수를 의미한다.  
2개의 행이 있고, id는 각각 0,1이다.  
condiment는 항상 0이고 사용자가 앱을 통해 실행하면 사용자가 입력한 값으로 업데이트되어 아두이노가 수신한 후 다시 0으로 변경된다.

```sql
mysql> desc ino;
+-----------+------+------+-----+---------+-------+
| Field     | Type | Null | Key | Default | Extra |
+-----------+------+------+-----+---------+-------+
| id        | int  | YES  |     | NULL    |       |
| condiment | int  | YES  |     | NULL    |       |
+-----------+------+------+-----+---------+-------+
```

### [PHP](https://github.com/eun-seong/NOA20_SSM/tree/master/PHP)

서버에서 실행되는 php 파일

#### inoExecuteData.php

오류를 줄이기 위해 데이터를 '/'를 붙여 전송한다.  
아두이노는 '/'가 붙은 데이터만 수신한다.

```php
$data = array();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
extract($row);

try {
    $stmt = $con->prepare("UPDATE ino SET condiment=0 WHERE id={$id}");
} catch (PDOException $e) {
    die("Database error: " . $e->getMessage());
}

echo "*" . $row['condiment'];
```

## [Arduino](https://github.com/eun-seong/NOA20_SSM/tree/master/Arduino)

아두이노에서 실행되는 파일

- 모터a  
  조미료통 안에 있는 모터로, 조미료가 습도로 인해 굳을 경우를 대비해 분쇄해주는 역할을 한다.
- 모터b  
  조미료를 분출할 때 사용되는 모터로, 통과 연결되어 있는 스크류를 돌려 조미료를 뒤에서 앞으로 밀어내는 역할을 한다.

#### Main.ino

모터 제어와 로드셀 입력 부분을 담당  
분출한 양이 목표 무게의 절반을 넘어가면 멈춤  
-> 분출구와 저울 간의 거리로 인해 실제 측정된 무게와 최종 측정 무게에 차이가 있기 때문에 이 차이를 줄이기 위해 모터의 동작을 제어한다.

```c++
// 설정한 calibration factor 값을 적용
scale.set_scale(calibration_factor);
...
while (weight < target && operation == 1) {
  //모터b 시계 방향 작동
  analogWrite(downmotor1, 200);
  //모터b 시계 방향으로 작동시키기 위해 반시계 방향을 0으로설정
  analogWrite(downmotor2, 0);
  //모터a 시계 방향 작동
  analogWrite(uppermotor1, 200);
  //모터a 시계 방향으로 작동시키기 위해 반시계 방향을 0으로설정
  analogWrite(uppermotot2, 0);

  // 분출한 양이 목표무게의 절반을 넘어가면 일정 간격으로 모터가 멈춤
  if (weight >= target * 0.5) {
    delay(50);
    analogWrite(downmotor1, 0);
    analogWrite(downmotor2, 0);
    analogWrite(uppermotor1, 0);
    analogWrite(uppermotot2, 0);
  }
  weight = scale.get_units() * 453.592;
  Serial.print(weight);
  Serial.println(" g");
  delay(100);
}
// 분출한 양이 목표 무게에 도달하면 모터를 멈춤
...
```

#### setting_factor.ino

로드셀의 설계에 따라 calibration factor 값이 달라지므로 기기 완성 후 팩터값을 찾는 동작을 최초 한 번 해야 한다.

```c++
// 로드셀로 측정한 무게(weight)와 실제 무게(standard)가 같을 때 까지 반복
while (standard != weight)
{
  scale.set_scale(calibration_factor);
  weight = scale.get_units() * 453.592;
  if (standard < weight)
    calibration_factor += 1000;	//측정 무게가 실제 무게보다 클 때 calibration_factor값이 1000씩 증가
  if (standard > weight)
    calibration_factor -= 1000;	//측정 무게가 실제 무게보다 작을 때 calibration_factor값이 1000씩 감소
  Serial.print("Reading: ");
  delay(200);
  Serial.print(weight, 1);
  Serial.print(" g");
  Serial.print("standard : ");
  Serial.print(standard);
  Serial.print(" calibration_factor: ");
  Serial.print(calibration_factor);
  Serial.println();
}
```

#### nodeMCU.ino

서버에서 실행할 분출 수치를 받아 온 후 우노 보드로 시리얼 통신을 통해 그 값을 전송한다.

```c++
char c = client.read();		//서버로부터 받아오는 문자값
if (c == '*') {
  while (client.available()) {
    char c = client.read();	//서버로부터 받아오는 문자값
    Serial.println(c);		//nodemcu시리얼창에 받아온값 표시
    SPI.transfer(c);		//spi 통신에서 데이터를 보내고 받는 함수
    str += c; 			//받아온 c값들을 str으로 묶어서 표시
    Serial.println(str);	//str값을 nodemcu상에 표시
  }
}
...
mySerial.println(str);/	/아두이노쪽으로 서버로부터 받아온 str을 송신
```
