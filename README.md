# Data Synchronization
일정 주기로 데이터 동기화 작업을 진행하는 백그라운드 프로그램입니다.

## 실행 방법

### 설정 파일 추가
resources 경로에 application.yml 파일을 생성하고 다음과 같이 설정해야 합니다.
datasource.source, datasource.target, datasource.quartz는 각각 source RDB, target RDB, quartz RDB 정보를 설정해야 합니다.

```yaml
spring:
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
