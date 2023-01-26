package com.task.concurrency;

import com.task.model.Task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/*
Can act as a rate limiter
 */
public class Concurrency {

    public static final int MAX_CONCURRENT = 50;
    static final AtomicInteger CONCURRENT_REQUESTS = new AtomicInteger();

    public static Task protect(Supplier<Task> supplier) {
        try {
            if (CONCURRENT_REQUESTS.incrementAndGet() > MAX_CONCURRENT) {
                throw new UnsupportedOperationException("max concurrent requests reached");
            }
            return supplier.get();
        }  finally {
            CONCURRENT_REQUESTS.decrementAndGet();
        }
    }
}
