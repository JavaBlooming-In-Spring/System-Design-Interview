package system.design.interview.traffic;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClientRequest;

@RestController
public class TrafficGenerator {

    private static final String URL = "http://localhost:8080";
    private static final WebClient webClientForTest = WebClient.create();
    private static final AtomicInteger successCounter = new AtomicInteger(0);
    private static final AtomicInteger totalSuccessCounter = new AtomicInteger(0);
    private static final AtomicInteger failedCounter = new AtomicInteger(0);

    /**
     * 사용이 끝나면 requestPool을 null로 만듬으로써 pool 반납.
     */
    @Nullable
    private static AtomicInteger requestPool;

    /**
     * @param durationSecond  트래픽 발생을 지속할 시간 (단위: second)
     * @param path            host, port를 제외한 path
     * @param requestPoolSize 트래픽 속도를 제어하기 위한 pool size. webClient의 최대 connection size는 1000이지만 되도록 낮은 값으로 설정해야 함. (default 100)
     * @return 매 초당 트래픽을 String으로 보내줌.
     */
    @GetMapping("/traffic")
    public String test(
            @RequestParam(defaultValue = "10") long durationSecond,
            @RequestParam(defaultValue = "/test") String path,
            @RequestParam(defaultValue = "100") int requestPoolSize,
            @RequestParam(defaultValue = "GET") String _httpMethod
    ) {
        final HttpMethod httpMethod = HttpMethod.valueOf(_httpMethod);
        final StringBuilder result = new StringBuilder();
        final TimeChecker timeChecker = TimeChecker.start(durationSecond);
        if (requestPool != null) {
            return "트래픽이 이미 발생중입니다.";
        }
        requestPool = new AtomicInteger(requestPoolSize);

        while (true) {
            // 트래픽 속도를 조절하기 위해 requestPool 만큼만 요청을 보냄.
            while (requestPool.get() <= 0) {
                if (requestPool.get() < 0) {
                    System.out.println("오잉?");
                }
            }

            result.append(timeChecker.calculateTrafficPerSec(successCounter));
            if (timeChecker.isEnd()) {
                break;
            }

            requestPool.decrementAndGet();
            webClientForTest.method(httpMethod)
                    .uri(URL + path)
                    .httpRequest(httpRequest -> {
                        HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                        reactorRequest.responseTimeout(Duration.ofSeconds(5));
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .doFinally((i) -> requestPool.incrementAndGet())
                    .timeout(Duration.ofSeconds(5))
                    .subscribe(
                            (success) -> {
                                successCounter.incrementAndGet();
                                totalSuccessCounter.incrementAndGet();
                            },
                            (error) -> {
                                System.out.println("failed! {" + failedCounter.incrementAndGet() + "}");
                            });
        }
        while (requestPool.get() < requestPoolSize) {
            timeChecker.calculateTrafficPerSec(successCounter);
        }
        requestPool = null;
        return result.toString();
    }

    @GetMapping("/success-get")
    public int getSuccess() {
        return successCounter.get();
    }

    @GetMapping("/pool-get")
    public String getPool() {
        if (requestPool == null) {
            return "requestPool이 없습니다.";
        }
        return String.valueOf(requestPool.get());
    }
}
