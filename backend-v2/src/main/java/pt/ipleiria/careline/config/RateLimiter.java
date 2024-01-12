package pt.ipleiria.careline.config;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class RateLimiter {

    private final long timeWindowInSeconds;
    private final int maxRequests;
    private final AtomicLong lastRequestTime;
    private final AtomicLong requestCount;

    public RateLimiter(long timeWindowInSeconds, int maxRequests) {
        this.timeWindowInSeconds = timeWindowInSeconds;
        this.maxRequests = maxRequests;
        this.lastRequestTime = new AtomicLong(Instant.now().getEpochSecond());
        this.requestCount = new AtomicLong(0);
    }

    public synchronized boolean isAllowed() {
        long currentTime = Instant.now().getEpochSecond();
        if (currentTime - lastRequestTime.get() > timeWindowInSeconds) {
            lastRequestTime.set(currentTime);
            requestCount.set(0);
        }

        return requestCount.incrementAndGet() <= maxRequests;
    }
}

