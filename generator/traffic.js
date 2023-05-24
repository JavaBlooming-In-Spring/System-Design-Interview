import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '5s', target: 10000 },
    { duration: '10s', target: 5000 },
    { duration: '5s', target: 10000 },
  ],
};

export default function() {
  const res = http.get('http://localhost:8080/test');
  check(res, { 'status was 200': (r) => r.status === 200 });
  sleep(1)
}