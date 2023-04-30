package system.design.interview.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TrafficGenerator {


  @GetMapping("/traffic")
  public String getTraffic(@RequestParam("thread") int thread, @RequestParam("timer") int timer) {
    ExecutorService executorService = Executors.newFixedThreadPool(thread);
    TestClient[] clients = new TestClient[thread];
    List<TestResult> results = new ArrayList<>();


    for (int i = 0; i < thread; i++) {
      final int index = i;
      clients[i] = new TestClient(timer);
      executorService.execute(() -> {
        TestResult result = clients[index].run();
        synchronized (results) {
          results.add(result);
        }
      });
    }

    executorService.shutdown();

    try {
      executorService.awaitTermination(timer*2L, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    double totalRequestCount = 0;
    double totalResponseTime = 0;
    double totalSuccessCount = 0;

    for (TestResult result : results) {
      totalRequestCount += result.getRequestCount();
      totalResponseTime += result.getResponseTime();
      totalSuccessCount += result.getSuccessCount();
    }

    log.info("총 Request 횟수 = " + totalRequestCount);
    log.info("요청 성공률 = " + String.format("%.3f",totalSuccessCount / totalRequestCount * 100) + "%");
    log.info("평균 Response 시간 = " +String.format("%.5f",totalResponseTime / 1000 / totalSuccessCount) + "ms");
    return "Finish";
  }
}
