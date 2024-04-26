# Data Synchronization

일정 주기로 데이터 동기화 작업을 진행하는 백그라운드 프로그램입니다.

## 실행 방법

### 설정 파일 추가

resources 경로에 application.yml 파일을 생성하고 다음과 같이 설정해야 합니다.
jta.atomikos.datasource.source, jta.atomikos.datasource.target에는 각각 source RDB, target RDB 정보를 입력합니다.
datasource.source, datasource.target, datasource.quartz는 각각 source RDB, target RDB, scheduler RDB 정보를 설정해야 합니다.

```yaml
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
```
