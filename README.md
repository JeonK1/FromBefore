# FromBefore
AngelHack2020 FromBefore Team

[개발문서 링크](https://docs.google.com/document/d/1EocbkrUlWa5vcP9SRFf-Bmn_i0vSo1Scv2Q8S0O1BxY/edit)


1. Github 링크
=============

구분
역할
링크
클라이언트
안드로이드 스튜디오를 통한 안드로이드 디바이스 구동
https://github.com/ImInnocent/2020angelhackserver
서버
장고를 통한 서버 구동
https://github.com/JeonK1/FromBefore



2. 개발 내용
=============

서버
-------------
  Django를 이용해 데이터 관리
  Http 통신을 통해 데이터를 저장하고 보내는 역할을 함
  AWS EC2를 통해 서버 인스턴스를 생성

클라이언트
-------------
  Android Studio를 통해 안드로이드 어플리케이션 개발
  개발언어로 Kotlin을 사용
  유저가 작성한 메시지를 서버로 전송 및 불러오기
  유저의 정보를 입력 받고 그에 맞는 서비스 제공

서버 / 클라 통신 내용
-------------
  메시지 가져오기
  클라 -> 서버: 얻고자 하는 D-day를 인자로 보냄. 인자가 없을 시, 전체 범위로 검색
  서버 -> 클라: 해당 D-day에 해당하는 메시지 중 하나를 Json의 형태로 전달

메시지 쓰기
-------------
  클라 -> 서버: 작성한 날짜의 D-day와 내용을 전달
  서버 -> 클라: 메시지를 검증하여 DB에 추가한 후, 응답을 보냄


3. 개발 환경
=============

Django
-------------
  Django: 3.0.x
  Python: 3.0.x

Linux(AWS EC2)
-------------
  Ubuntu 18.04 LTS

Android Studio
-------------
  Android Studio 4.0
  Java 14.0.x
  JDK 1.8
