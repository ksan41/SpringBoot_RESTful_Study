# SpringBoot_RESTful_Study
 
이동원 님의 Spring Boot를 이용한 RESTful Web Services 개발
강의를 들으며 실습한 내용을 정리, 저장합니다.


//마이크로 서비스 아키텍쳐
하나의 큰 애플리케이션을 여러 개의 작은 애플리케이션으로 쪼개어 변경과 조합이 가능하도록 만든 아키텍처.
각 서비스들 끼리 독립적으로 개발되어있어야함.
애플리케이션을 특화된 기능별로 나누게 되면 자연스럽게 애플리케이션의 추상화(abstraction)가 가능.
-각 서비스에 최적화된 언어, 데이터베이스를 선택할 수 있어야함.
-표준화된 HTTP프로토콜을 사용하는 RESTful 이 많이 사용됨.

//HTTP 통신구조?
-클라이언트가 서버에 요청을 보내고, 그 요청에 대한 응답을 서버가 클라이언트에 보내는 구조.
-요청 : HTTP Request / 응답 : HTTP Response
-여기서 클라이언트란? Windows 응용프로그램이 될 수도 있고, Web, Android, IOS가 될 수 있다.


0.개요
1.서비스를 개발하기 위해 사용될 Spring Boot와, RESTful Service 개발하는 방법
2.User Service API 를 사용해 개발할것.
3.필터링, 버전관리 등 기능확장
4.스프링부트에서 제공하는 api활용 REST 문서화
5.관계형 데이터베이스, JPA 사용
6.더 나은 설계를 위한 가이드




---

# Web  Service와 Web Application

//웹서비스란?(Web Service)
월드와이드웹(WWW,Internet,HTTP)를 이용한 디바이스 간의 통신서비스.
네트워크 상에서 서로 다른 종류의 컴퓨터들 간 상호작용을 하기 위한 소프트웨어 시스템
구현의 복잡성, 표준화를 개선함.
xml을 사용하기 때문에 주고받는 데이터의 통일성이 강조됨.
-워싱과 머신. 어플리케이션간의 상호작용을 위한 설계
-플랫폼 독립적인 구조
-다양한 시스템, 어플리케이션간의 서비스제공

//웹 어플리케이션이란?(Web Application)
서버에 저장되어있고, 웹 브라우저를 실행할 수 있는 프로그램.어플리케이션.
웹메일, 인터넷 뱅킹, 포털서비스, 커뮤니티 등등...
사용자(클라이언트)가 웹브라우저를 사용하게됨.
웹 어플리케이션을 실행, 데이터베이스에 접근.
웹 어플리케이션을 실행하는 서버가 웹 어플리케이션 서버.
웹서버와는 달리, 동적인 컨텐츠 처리.


사용자(클라이언트)   요청->   서버
                    응답<-

