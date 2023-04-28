package system.design.interview.traffic;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TimeChecker {

    private long startTime;
    private long prev;
    private long durationSecond;

    public static TimeChecker start(long durationSecond) {
        long startTime = System.currentTimeMillis();
        return new TimeChecker(startTime, startTime, durationSecond);
    }

    public Optional<String> calculateTrafficPerSec(AtomicInteger successCounter) {
        long now = System.currentTimeMillis();
        if (now - prev >= 1000) {
            String traffic = "[%ss] 초당 트래픽: %d %n".formatted((now - startTime) / 1000, successCounter.getAndSet(0));
            System.out.printf(traffic);
            prev = now;
            return Optional.of(traffic);
        }
        return Optional.empty();
    }

    public boolean isEnd() {
        return (System.currentTimeMillis() - startTime) / 1000 >= durationSecond;
    }
}
