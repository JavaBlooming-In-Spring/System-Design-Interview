# System-Design-Interview

가상 면접 사례로 배우는 대규모 시스템 설계 기초 구현

## Quick Start

프로메테우스 권한 추가

```shell
sudo chmod -R 777 ./prometheus
```

프로메테우스 접속 방법

```markdown
http://localhost:9090
```

grafana 접속 방법

```markdown
http://localhost:3000/

## 아이디 / 비밀번호

admin / admin

## Datasource 추가

좌측 톱니바퀴 - Data source - Prometheus 클릭
URL에 http://prometheus:9090 입력 후 "Save & Test"

## JVM Dashboard 추가

좌측 Dashboard - Import - 아래 url 입력 후 Load
https://grafana.com/grafana/dashboards/4701-jvm-micrometer/
이 후 Datasource에 Prometheus 선택하고 Import
```
