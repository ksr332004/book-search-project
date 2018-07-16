book-search-project
=============================
> Spring-Boot와 AngularJS을 이용한 Web 프로젝트

### Dependencies
* JDK 1.8
* Spring-Boot 2.0.2
   * Gradle
   * JPA
   * MySQL
   * Web
   * JWT
* Angularjs 2.x
   * [Blur Admin](https://github.com/akveo/blur-admin)

### Getting Started
MySQL 설정
Schema에 web 생성
```
Host : 127.0.0.1
Post : 3306
Username : root
Password : seran
Schema : web
```

Server 실행
book-search-project\app-server 경로에서 다음 명령어를 실행
```bash
#Eclipse IDE Import 할 경우 [참조](http://projooni.tistory.com/entry/SpringBoot-eclipse%EC%97%90%EC%84%9C-springboot-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-clone%ED%95%98%EA%B8%B0)

#Local Terminal에서 명령어를 수행하는 것을 권장함

#build
gradle build
#윈도우OS의 경우
gradlew build

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

### 구현 된 기능
- 서버 API

| Mtehod   |Path                              | RequestParam                         | ResponseBody                 |
|----------|:---------------------------------|:------------------------------------:|------------------------------|
| POST     | /api/auth/login                  | email, password                      | 로그인                        |
| POST     | /api/auth/signup                 | email, password, name                | 회원가입                      |
| GET      | /refresh                         |                                      | 토큰 재연장                   |
| POST     | /api/search/book                 | query, sort, page, size ...          | 책 검색 / 검색 키워드 저장     |
| GET      | /api/search/history              |                                      | 최근 검색 히스토리 (최근10건)  |
| GET      | /api/bookmark/view               | title, sort, page                    | 북마크 검색                   |
| POST     | /api/bookmark/add                | Document                             | 북마크 등록                   |
| DELETE   | /api/bookmark/{id}               | 북마크 ID                             | 북마크 삭제                   |

- ERD

![erd](https://user-images.githubusercontent.com/41044894/42779936-cb54d952-897c-11e8-9381-beb0e52d36e0.png)


### 구현 중 기능
- JWT 기반 AngulerJS Client 화면 연동


### Visit
Client (Main)
- http://localhost:3000/

Server
- http://localhost:8080/
