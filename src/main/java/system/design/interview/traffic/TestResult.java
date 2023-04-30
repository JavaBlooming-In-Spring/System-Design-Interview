package system.design.interview.traffic;

import lombok.Getter;

@Getter
public class TestResult {

  private Long requestCount;
  private Long successCount;
  private Long responseTime;

  public TestResult(Long requestCount, Long successCount, Long responseTime) {
    this.requestCount = requestCount;
    this.successCount = successCount;
    this.responseTime = responseTime;
  }
}
