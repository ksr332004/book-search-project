book-search-project
=============================
> Spring-Boot와 AngularJS을 이용한 Web 프로젝트

### Branchs
* Default branch : master
* Active branch : develop
* Active branch : hotfix (modify and update)
* Active branch : release (client: bower_components & node_modules zip file upload)


### IDE TOOL
* [IntelliJ Community](https://www.jetbrains.com/idea/download)
* [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/)


### Dependencies
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Spring-Boot 2.0.2
   * Gradle
   * Spring Security
   * JWT
   * Lombok
   * Querydsl
   * JPA
   * [MySQL](https://dev.mysql.com/downloads/)
   * H2
* AngularJS
   * [Node.js](https://nodejs.org/ko/)
   * [Blur Admin](https://github.com/akveo/blur-admin)


### Getting Started
**1. Git Clone**
```
cd {directory} #원하는 경로로 이동
git clone https://github.com/kimsr2004/book-search-project
```


**2. H2 Database 설치/확인**

*Server를 실행 시키면 자동으로 실행*
* 확인
```
#URL접근
http://localhost:8080/h2-console

#JDBC URL
jdbc:h2:mem:testdb
```

~~**2. MySQL 설치/설정/실행**~~

~~*MySQL 설치 후 Schema에 web 생성*~~
~~* MySQL 설치 [다운로드](https://dev.mysql.com/downloads/)~~
~~* 설정~~
```
#설정
Host : 127.0.0.1
Post : 3306
Username : root
Password : seran
Schema : web
```
~~* MySQL 실행~~


**3. Server 실행**

* IntelliJ로 실행할 경우
   * Open > {directory}\book-search-project\app-server
   * Use auto-import : check
   * Gradle JVM : select use JDK
   * Lombok Install
      * File > Settings > Plugins > [Click] Browse Repositories > [Install] Lombok Plugin > IntelliJ Restart
      * File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors > [Check] Enable annotation processing


* Terminal로 실행할 경우

   * book-search-project\app-server 경로에서 다음 명령어를 실행
```bash
#Local Terminal에서 명령어를 수행하는 것을 권장함

#go to directory
cd {directory}\book-search-project\app-server

#build
gradle build
#윈도우OS의 경우
gradlew build

#start
gradle bootRun
```


**4. Client 실행**

* npm을 이용하여 설치할 경우
*book-search-project\app-client 경로에서 다음 명령어를 실행*
```bash
#go to directory
cd {directory}\book-search-project\app-client

#install
npm install --g bower
npm install --global gulp
npm install

#go to directory
cd {directory}\book-search-project\app-client\src\app

#start
gulp serve        #로컬 실행
gulp serve:dist   #로컬 실행 및 배포용 소스 생성
gulp release      #배포용 소스 생성
```

* bower_components & node_modules zip 파일을 이용해 실행할 경우
   1. 각각 app-client/bower_components와 app-client/node_modules에 압축 해제
   2. app-client/node_modules/.bin 경로에서 gulp serve 명령어 실행



### 구현 된 기능
* 서버 API

| Mtehod   |Path                              | RequestParam                               | ResponseBody                 |
|----------|:---------------------------------|:------------------------------------------:|------------------------------|
| POST     | /api/auth/login                  | email, password                            | 로그인                        |
| POST     | /api/auth/signup                 | email, password, name                      | 회원가입                      |
| GET      | /api/auth/refresh                |                                            | 토큰 재연장                   |
| GET      | /api/user/info                   |                                            | 사용자 정보 가져오기           |
| PUT      | /api/user/update                 | password, name                             | 사용자 정보 업데이트           |
| PUT      | /api/user/delete                 |                                            | 사용자 disable 처리           |
| GET      | /api/search/book                 | query, target, sort, page, size, category  | 책 검색 / 검색 키워드 저장     |
| GET      | /api/search/history              |                                            | 최근 검색 히스토리 (최근20건)  |
| GET      | /api/bookmark/view               | query, target, sort, page                  | 북마크 검색                   |
| POST     | /api/bookmark/add                | Document                                   | 북마크 등록                   |
| DELETE   | /api/bookmark/{id}               | 북마크 ID                                   | 북마크 삭제                   |


* ERD

![erd](https://user-images.githubusercontent.com/41044894/42779936-cb54d952-897c-11e8-9381-beb0e52d36e0.png)


* 화면 메뉴
   * 회원가입
   * 로그인
   * 책 검색
   * 북마크
   * 내 정보
   * 로그아웃



### Visit
Client (Main)
- http://localhost:3000/

Server
- http://localhost:8080/
