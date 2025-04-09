package br.com.test.technical.components;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Component
public class RateLimiter {
    private final Deque<Instant> requestTimestamps = new ArrayDeque<>(9);
    private final Lock lock = new ReentrantLock();
    Logger logger = Logger.getLogger(RateLimiter.class.getName());

    private static final int MAX_REQUEST_PER_SECOND = 8;
    private static final Duration WINDOW_INTERVAL = Duration.ofSeconds(1);

    public void acquire() {
        try {
            lock.lock();
            Instant now = Instant.now();

            //remove os timestamps cujo invervalo até o momento atual é maior do que o permitido
            while (!requestTimestamps.isEmpty() && Duration.between(requestTimestamps.peekFirst(), now).compareTo(WINDOW_INTERVAL) > 0) {
                requestTimestamps.removeFirst();
            }

            if(requestTimestamps.size() >= MAX_REQUEST_PER_SECOND) {
                Instant oldest = requestTimestamps.peekFirst();
                long waitingTime = WINDOW_INTERVAL.toMillis()  - Duration.between(oldest, now).toMillis();

                if(waitingTime > 0) {
                    logger.info(String.format("Waiting %d ms before execute the next request.", waitingTime));
                    Thread.sleep(waitingTime);
                }

            }
            requestTimestamps.addLast(Instant.now());
        } catch(Exception ex){
            logger.severe(ex.getMessage());
        } finally {
            lock.unlock();
        }
    }


}
