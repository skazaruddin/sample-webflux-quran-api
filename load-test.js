import http from 'k6/http';
import { sleep } from 'k6';

// Define the scenarios
export const options = {
         scenarios : {
              lowLoad: {
                executor: 'ramping-vus',
                startVUs: 10,
                stages: [
                  { duration: '1m', target: 20 }, // Ramp up to 20 VUs over 1 minute
                  { duration: '1m', target: 0 }, // Ramp down to 0 VUs over 1 minute
                ]
              },
              mediumLoad: {
                executor: 'ramping-vus',
                startVUs: 50,
                startTime: '5m',
                stages: [
                  { duration: '1m', target: 100 }, // Ramp up to 100 VUs over 1 minute
                  { duration: '2m', target: 100 }, // Stay at 100 VUs for 2 minutes
                  { duration: '1m', target: 0 }, // Ramp down to 0 VUs over 1 minute
                ]
              },
              highLoad: {
                executor: 'ramping-vus',
                startVUs: 100,
                startTime: '10m',
                stages: [
                  { duration: '1m', target: 200 }, // Ramp up to 200 VUs over 1 minute
                  { duration: '2m', target: 200 }, // Stay at 200 VUs for 2 minutes
                  { duration: '1m', target: 0 }, // Ramp down to 0 VUs over 1 minute
                ]
              },
        },
        thresholds: {
              http_req_duration: ['p(95)<500'], // 95% of requests should complete within 500ms
        },
};

// Define the default function
export default function () {
  // Make a GET request to the endpoint
  const response = http.get(`http://localhost:8080/v1/surah/114/en.asad`);

  // Check if the response status code is 200
  if (response.status === 200) {
    console.log(`Successfully retrieved Surah from the Quran.`);
  } else {
    console.error(`Failed to retrieve Surah from the Quran. Status code: ${response.status}`);
  }

  // Sleep for a random duration between 1 to 3 seconds before making the next request
  sleep(Math.random() * 2 + 1);
}