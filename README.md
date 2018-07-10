book-search-project
=============================
> Spring-Boot와 AngularJS을 이용한 Web 프로젝트

### Dependencies
* Spring-Boot 2.0.2
   * Gradle 2.x
   * JPA
   * H2
   * MySQL
   * Web
* Angularjs
   * Blur Admin

### Getting Started
Server 실행
book-search-project\app-serve 경로에서 다음 명령어를 실행
```bash
#build
gradle build
#start
gradle bootRun
```

Client 실행
book-search-project\app-client 경로에서 다음 명령어를 실행
```bash
#install
npm install --g bower
npm install --global gulp
npm install
#start
gulp serve        #로컬 실행
gulp serve:dist   #로컬 실행 및 배포용 소스 생성
gulp release      #배포용 소스 생성
```
