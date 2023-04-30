package system.design.interview.traffic;

import java.net.http.HttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestClient {
  private final String destination = "http://localhost:8080";
  private final int timer;

  public TestClient(int timer) {
    this.timer = timer;
  }

  public TestResult run() {
    RestTemplate restTemplate = new RestTemplate();
    long requestCount = 0;
    long successCount = 0;
    long totalTime = 0;

    long endTime = System.currentTimeMillis() + timer * 1000L;
    while (System.currentTimeMillis() < endTime) {
      try {
        requestCount++;
        long startTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.getForEntity(destination + "/test", String.class);
        long elapsedTime = System.currentTimeMillis() - startTime;
        totalTime += elapsedTime;
        if (response.getStatusCode().is2xxSuccessful()) {
          successCount++;
        }
      } catch (Exception e) {
        // 실패한 경우 무시
        System.out.println("false = " + false);
      }
    }

    return new TestResult(requestCount, successCount, totalTime);
  }
}
