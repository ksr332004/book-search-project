book-search-project
=============================
> Spring-Boot와 AngularJS을 이용한 Web 프로젝트

### Dependencies
* JDK 1.8
* Spring-Boot 2.0.2
   * Gradle 2.x
   * JPA
   * H2
   * MySQL
   * Web
* Angularjs 2.X
   * Blur Admin

### Getting Started
Server 실행
book-search-project\app-server 경로에서 다음 명령어를 실행
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

### 개발된 기능 (서버 API)
| Mtehod   |Path                              | RequestParam                         | ResponseBody                 |
|----------|:---------------------------------|:------------------------------------:|------------------------------|
| POST     | /api/auth/login                  | email, password                      | 로그인                        |
| POST     | /api/auth/signup                 | email, password, name                | 회원가입                      |
| GET      | /refresh                         |                                      | 토근 재연장                   |
| POST     | /api/search/book                 | query, sort, page, size ...          | 책 검색                      |
| GET      | /api/search/history              |                                      | 사용자 검색 히스토리검색       |
| GET      | /api/bookmark/view               | title, sort, page                    | 북마크 검색                   |
| POST     | /api/bookmark/add                | Document                             | 북마크 등록                  |
| DELETE   | /api/bookmark/{id}               | 북마크 ID                            | 북마크 삭제                  |

### Visit
- http://localhost:3000/