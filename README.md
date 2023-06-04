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
---

## K6 사용하기(macOS ver)
1. K6 설치
```shell
brew install k6
```

2. k6 스크립트 작성(기본 예제)
```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '1m30s', target: 10 },
        { duration: '20s', target: 0 },
    ],
};

export default function() {
    const res = http.get('http://localhost:8080/test');
    check(res, { 'status was 200': (r) => r.status === 200 });
    sleep(1)
}
```

3. 스크립트 실행
```shell
k6 run ./generator/traffic.js
```

### [공식문서](https://k6.io/docs/)

### 로드 밸런서 추가하기

1. docker compose를 활용해, 동일한 was 이미지를 2개 띄우기
```
docker compose up --scale app=2
```

띄워진 컨테이너 확인

![image](https://github.com/JavaBlooming-In-Spring/System-Design-Interview/assets/76645095/fc1af713-da59-4977-849c-085b803a2316)


2. 요청을 2번 보냈을때, 로드 밸런싱이 되는지 확인

![image](https://github.com/JavaBlooming-In-Spring/System-Design-Interview/assets/76645095/b5feb147-0a62-47c8-849f-ad0bfaef37f9)

    a. app-2 컨테이너 종료해보기
    
![image](https://github.com/JavaBlooming-In-Spring/System-Design-Interview/assets/76645095/5765b9f2-aff4-487c-96bf-f3f2686186da)

    b. 다시 요청을 보냈을때 app-1이 요청을 받는지 확인하기

![image](https://github.com/JavaBlooming-In-Spring/System-Design-Interview/assets/76645095/8e4e9514-7d57-41db-94e6-c3802f5d4111)
