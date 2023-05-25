# System-Design-Interview

가상 면접 사례로 배우는 대규모 시스템 설계 기초 구현

## Quick Start

프로메테우스 권한 추가

 ```shell
 sudo chmod -R 755 ./prometheus
 ```

### 프로메테우스 접속 방법

 ```markdown
 http://localhost:9090
 ```

### grafana 접속 방법

 ```markdown
 http://localhost:3000/
```

### 아이디 / 비밀번호

```
admin / admin
```

### prometheus Datasource 추가

<img width="1113" alt="image" src="https://user-images.githubusercontent.com/68914294/235339596-397e2623-d884-46f4-9664-7d2479fefd8a.png">

1. 가운데 DATA SOURCES 클릭
2. Prometheus 클릭

<img width="645" alt="image" src="https://user-images.githubusercontent.com/68914294/235339668-4ece0ae2-113a-4b9f-9195-b31778348521.png">

1. HTTP URL 위와 같이 설정 (docker-compose 파일의 service 이름과 동일하게 맞춘다)
2. 페이지 하단의 "Save & Test" 클릭

### JVM Dashboard 추가

<img width="227" alt="image" src="https://user-images.githubusercontent.com/68914294/235339788-7448e251-afd3-49af-84cd-1796b7971415.png">

+Import 클릭


<img width="668" alt="image" src="https://user-images.githubusercontent.com/68914294/235339919-a9befe79-977f-461a-a18e-44e6a5753908.png">

- 4701 또는 https://grafana.com/grafana/dashboards/4701-jvm-micrometer/ 을 입력하고 Load 클릭

<img width="629" alt="image" src="https://user-images.githubusercontent.com/68914294/235340098-97307cba-ad30-4e88-a220-01cd319e1d1d.png">

이 후 Prometheus 선택하고 Import

### Result

<img width="2553" alt="image" src="https://user-images.githubusercontent.com/68914294/235340155-0023265b-1144-4072-8fc7-c2066ab65aec.png">

### Mysql Dashboard 추가

위와 동일하게 +Import 에서 아래 숫자를 입력하고 Load를 클릭

- 7362
