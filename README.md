# 스마트 조미료 추출기 ( SSM; Smart Spice Machine )



## Description

> 2020.03. - 2020.10.

### Awards

* 🏅 2020 교내 Deep Change 인하공대 Start-up Challenge 최우수상



### 요약

* 요리 초보가 계량을 쉽게 할 수 있도록 기획
* 사용자가 자주 사용하는 레시피를 모바일 앱에 저장
* 앱에서 레시피를 실행하면 조미료통은 지정된 수치만큼 조미료를 분출





## About Project

### Machine

<img src="https://img.shields.io/badge/Language-C / C++-green?style=flat"/><img src="https://img.shields.io/badge/Platform-Arduino-blue?style=flat"/>

* 앱에서 지정한 수치만큼 조미료 배출
* 기기 내 회전체를 통해  조미료 덩어리 분쇄
* 조미료가 직접 닿는 부분을 분리하여 위생적인 관리
* 영점을 맞추는 스위치로 개인 그릇의 사용을 가능하게 함



### Android App

<img src="https://img.shields.io/badge/Language-Java-green?style=flat"/><img src="https://img.shields.io/badge/Platform-Android-blue?style=flat"/>

* 사용자가 입력한 레시피 서버 DB에 저장
* '레시피 실행'과 '바로 실행' 기능 구현
* 실행할 조미료를 서버에 request



### BE

<img src="https://img.shields.io/badge/Language-PHP-green?style=flat"/><img src="https://img.shields.io/badge/Platform-Arduino-blue?style=flat"/><img src="https://img.shields.io/badge/DB-MySQL-yellow?style=flat"/>

* AWS free tier 사용
* MySQL 사용자 DB 생성
* 사용자가 실행한 조미료 데이터를 조미료통의 NodeMCU에게 json으로 전송





## Results

### H/W

- Machine

<img src=images/machine1.png  width="30%"/><img src=images/machine2.png  width="30%"/>



- Circuit

<img src=images/circuit.png  width="60%"/>







### App

| 기능             | 화면                                               |
| ---------------- | -------------------------------------------------- |
| 초기화면         | <img src=images/android_list.png  width="50%"/>    |
| 조미료 실행 명령 | <img src=images/android_execute.png  width="50%"/> |
| 레시피 추가      | <img src=images/android_add.png  width="50%"/>     |





***

### References

- HTTP 통신 : [webnautes](https://webnautes.tistory.com/1189)

