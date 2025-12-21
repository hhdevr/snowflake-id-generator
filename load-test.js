import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
    stages: [
        {duration: '1m', target: 50},
        {duration: '4m', target: 100},
        {duration: '3m', target: 100},
        {duration: '2m', target: 0},
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],
        http_req_failed: ['rate<0.005'],
    },
};

export default function () {
    http.get('http://localhost:30080/');
    sleep(0.01);
}
