package org.jcm.gs.rest.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@AllArgsConstructor
public class GreetingService {

    private final ThreadPoolTaskExecutor defaultThreadPool;
    private final AtomicLong counter = new AtomicLong();

    public String greeting(String name) {
        Future<String> submit = defaultThreadPool.submit(getString(name));
        while (!submit.isDone()) {
            log.info("-->");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("睡眠异常", e);
                throw new RuntimeException(e);
            }
        }

        String result = null;
        try {
            result = submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Callable<String> getString(String name) {
        return () -> {
            String result = "zhangsan" + "-" + name + "-";
            return result + counter.incrementAndGet();
        };
    }
}
