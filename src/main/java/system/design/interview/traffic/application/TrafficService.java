package system.design.interview.traffic.application;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/*
- Tomcat 최대 스레드 개수 : 200
 */
@Slf4j
@Service
public class TrafficService {

    private static final String REQUEST_URI = "http://localhost:8080/test";

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public TrafficService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.webClient = WebClient.create(REQUEST_URI);
    }

    public String generateSyncTraffic(final int durationOfSecond) {
        final AtomicInteger totalCounter = new AtomicInteger(0);
        final AtomicInteger successCounter = new AtomicInteger(0);
        final AtomicInteger failCounter = new AtomicInteger(0);

        final LocalDateTime startTime = LocalDateTime.now();
        final LocalDateTime endTime = startTime.plusSeconds(durationOfSecond);

        while (LocalDateTime.now().isBefore(endTime)) {
            syncRequest(successCounter, failCounter);
            totalCounter.incrementAndGet();
        }

        return generateMessage(totalCounter, successCounter, failCounter);
    }

    private void syncRequest(final AtomicInteger successCounter, final AtomicInteger failCounter) {
        try {
            restTemplate.getForEntity("http://localhost:8080/test", String.class);
            successCounter.incrementAndGet();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.warn("요청 실패 : " + e.getMessage());
            failCounter.incrementAndGet();
        }
    }

    private String generateMessage(
            final AtomicInteger totalCounter,
            final AtomicInteger successCounter,
            final AtomicInteger failCounter
    ) {
        return MessageFormat.format(
                "{0}번의 요청 중 {1}번의 성공, {2}번의 실패 => {3}%",
                totalCounter.get(),
                successCounter.get(),
                failCounter.get(),
                ((double) successCounter.get() / totalCounter.get()) * 100
        );
    }

    public void generateNonSyncTraffic(final int durationOfSecond) {
        final AtomicInteger totalCounter = new AtomicInteger(0);
        final AtomicInteger successCounter = new AtomicInteger(0);
        final AtomicInteger failCounter = new AtomicInteger(0);

        final Scheduler scheduler = Schedulers.newParallel("scheduler", 100);

        Flux.interval(Duration.ZERO, Duration.ofMillis(10))
                .take(Duration.ofSeconds(durationOfSecond))
                /*
                - concurrency는 한 번에 처리될 수 있는 병렬 작업의 최대 수를 결정
                - 너무 작게 설정하면 처리할 수 있는 것보다 많은 것이 들어오기에 적절한 설정이 필요
                 */
                .flatMap(request -> nonSyncRequest(), 100)
                .subscribeOn(scheduler)
                .doOnComplete(() -> log.info(generateMessage(totalCounter, successCounter, failCounter)))
                .subscribe(
                        success -> {
                            successCounter.incrementAndGet();
                            totalCounter.incrementAndGet();
                            log.info("요청 성공 : " + success);
                        },
                        fail -> {
                            failCounter.incrementAndGet();
                            totalCounter.incrementAndGet();
                            log.warn("요청 실패 : " + fail.getMessage());
                        }
                );
    }

    private Mono<String> nonSyncRequest() {
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
