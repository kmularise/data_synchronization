# 데이터 동기화 프로그램 개발

일정 주기로 데이터 동기화 작업을 진행하는 백그라운드 프로그램입니다.

## 📝 구현 내용

- [x] 테이블 데이터 동기화 알고리즘
- [x] 스케줄링 설정
- [x] source DB, target DB, 스케줄러 작업 정보 DB 연결 및 구축
- [x] source DB, target DB 데이터와 스케줄링 작업을 관리할 수 있는 UI 구성

<br/><br/><br/><br/>

## 💻 프로그램 실행

### Docker로 개발 환경 구성

두 개의 DB 컨테이너를 관리할 수 있는 개발환경을 편리하기 위해 Docker compose를 사용했습니다.
<br/>

1. 아래와 같이 docker-compose.yml 파일을 구성합니다. 이때, ${} 표시가 된 변수에는 지정한 값을 넣습니다.

```yaml
version: "3.8"

services:
  master:
    image: ubuntu/mysql
    container_name: source_mysql_db
    restart: always
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
      - --explicit_defaults_for_timestamp
      - --max_connections=1000
    environment:
      MYSQL_USER: ${SOURCE_DB_MYSQL_USER}
      MYSQL_PASSWORD: ${SOURCE_DB_MYSQL_USER_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${SOURCE_DB_ROOT_PASSWORD}
    ports:
      - '3307:3306'  # Host's port 3307 mapped to container's port 3306
    volumes:
      - ${SOURCE_MYSQL_VOLUME}:/var/lib/mysql
      - ${SOURCE_LOG_VOLUME}:/var/log/mysql
    user: mysql

  slave:
    image: ubuntu/mysql
    container_name: target_mysql_db
    restart: always
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
      - --explicit_defaults_for_timestamp
      - --max_connections=1000
    environment:
      MYSQL_USER: ${TARGET_DB_MYSQL_USER}
      MYSQL_PASSWORD: ${TARGET_DB_MYSQL_USER_PASSWORD}
      MYSQL_ROOT_PASSWORD: ${TARGET_DB_ROOT_PASSWORD}
    ports:
      - '3308:3306'  # Host's port 3308 mapped to container's port 3306
    volumes:
      - ${TARGET_MYSQL_VOLUME}:/var/lib/mysql
      - ${TARGET_LOG_VOLUME}:/var/log/mysql
    user: mysql

```

2. 다음 명령어를 통해 docker-compose.yml에서 지정된 DB 컨테이너들을 실행시킵니다.

```shell
docker compose up -d
```

3. source DB 컨테이너와 target DB 컨테이너에서 각각 source_schema와 target_schema를 만들고, sql/schema에 있는 sql 스크립트 파일 중
   source_tables.sql, scheduler_tables.sql은 source_schema 안에서 테이블을 만들고 target_tables.sql, temporary_target_tables.sql은 target_schema 안에서 테이블을 만들게 됩니다.

### 설정 파일 추가

main/resources 경로에 application.yml 파일을 생성하고 test/resources에 application-test.yml 파일을 생성하고 다음과 같이 설정해야 합니다.
jta.atomikos.datasource.source, jta.atomikos.datasource.target에는 각각 source RDB, target RDB 정보를 입력합니다.
datasource.source, datasource.target, datasource.quartz는 각각 source RDB, target RDB, scheduler RDB 정보를 설정해야 합니다.

````yaml
spring:
  jta:
    atomikos:
      datasource:
        source:
          borrow-connection-timeout: 10000
          max-lifetime: 20000
          max-pool-size: 25
          min-pool-size: 3
          unique-resource-name: source
          xa-data-source-class-name: com.mysql.cj.jdbc.MysqlXADataSource
          xa-properties:
            password: ****
            url: ****
            user: ****
        target:
          borrow-connection-timeout: 10000
          max-lifetime: 20000
          max-pool-size: 25
          min-pool-size: 3
          unique-resource-name: target
          xa-data-source-class-name: com.mysql.cj.jdbc.MysqlXADataSource
          xa-properties:
            password: ****
            url: ****
            user: ****
  quartz:
    job-store-type: memory
    properties:
      org:
        quartz:
          threadPool:
            threadCount: 5
    auto-startup: true
  datasource:
    source:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: ****
      username: ****
      password: ****
    target:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: ****
      username: ****
      password: ****
    quartz:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: ****
      username: ****
      password: ****
````

### 💻 프로그램 실행

프로그램 빌드 후 실행합니다.

```shell
./gradlew build
```

```shell
java -jar build/libs/synchronizer-0.0.1-SNAPSHOT.jar
```

<br/>

프로그램 실행 후 http://localhost:8080으로 접속하면 사용자 프로그램 UI를 사용할 수 있습니다. Java 17, Mac M1 환경에서 실행을 확인했습니다. 프로그램이 실행되지 않는다면 Java
버전, 도커 컨테이너 실행 여부, 포트 사용을 확인해보시길 권장드립니다.
> source DB 컨테이너(3307), target DB 컨테이너(3308)를 사용하기 때문에 해당 포트에서 실행 중인 프로그램 종료 후, 프로그램을 실행해야 합니다.

프로그램 실행 후 특정 URL로 접속하면 테이블 데이터 동기화 작업 목록, 데이터 동기화 실행 로그, source 데이터베이스 데이터 관리, target 데이터베이스 데이터 조회를 진행할 수 있습니다.

* [데이터 동기화 작업 목록](http://localhost:8080/tasks)
* [데이터 동기화 실행 로그](http://localhost:8080/task-logs)
* [source DB 테이블 목록](http://localhost:8080/table/source/collection)
* [target DB 테이블 목록](http://localhost:8080/table/target/collection)

## 📦패키지 구조

```
└── synchronizer
    ├── SynchronizerApplication.java
    ├── common # 공통 설정
    ├── mapper # mybatis mapper 설정
    ├── scheduler # 스케줄러
    ├── service # 서비스 계층
    └── web # 사용자 프로그램
```

## ✏️ 요구사항

| 요구사항명       | 요구사항 상세설명                                                                                                                                                                                                                                                                                                                                |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 테이블 동기화     | 동기화 대상 테이블은 행 데이터가 10000개 이하이고 기본키(primary key)가 ‘id’이어야 합니다. source DB 대상 테이블 데이터를 target DB의 임시 테이블로 옮깁니다. 그리고 target DB의 대상 테이블과 임시 테이블을 비교하고, 새롭게 삽입된 데이터, 수정된 데이터, 삭제된 데이터를 target DB에 반영합니다. 동기화 작업은 테이블 별로 병렬로 실행되어야 합니다. 동기화 과정에서 오류가 발생하면 예외를 발생시킵니다. source DB에서 변경이 일어나는 중에 동기화 작업이 실행되게 된다면, DB 변경 작업 단위가 끝나고 동기화 작업을 실행합니다. |
| 스케줄러 설정     | 설정된 동기화 주기로 테이블 동기화 작업을 실행되도록 스케줄러를 설정해야 합니다. 이때 동기화 주기는 분단위로 설정해야 합니다. 동기화 작업을 스케줄러에 등록하거나 삭제할 때 예외가 발생할 경우 적절히 처리해야 합니다.                                                                                                                                                                                                               |
| DB 연결       | source DB와 프로그램, target DB와 프로그램을 연결합니다. 애플리케이션 로딩 시에 각 DB의 연결을 확인하고 DB 연결이 되지 않으면 프로그램이 종료됩니다. 동기화 과정 중에 DB 연결이 되지 않으면 예외를 발생시킵니다.                                                                                                                                                                                                      |
| 작업 실행 로그 기록 | 스케줄러에 등록된 작업의 실행과 관련하여 시작 시간, 종료시간, 실행 상태를 기록합니다. 작업 실행이 진행중이면 대기 상태, 작업 실행이 성공하면 성공 상태, 작업 실행이 실패하면 실패 상태로 나타나야 합니다.                                                                                                                                                                                                                    |

## 📑 테스트 코드

단위 테스트, 통합 테스트 위주로 테스트를 작성했습니다.
<img width="884" alt="image" src="https://github.com/kmularise/data_synchronization/assets/106499310/bec97b7a-0c80-43ff-947d-59c0caaf6226">
<img width="882" alt="image" src="https://github.com/kmularise/data_synchronization/assets/106499310/ebea4033-3c88-49d3-bb38-9bcdf1896416">
