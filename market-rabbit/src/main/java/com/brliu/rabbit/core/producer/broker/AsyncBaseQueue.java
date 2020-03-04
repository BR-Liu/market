package com.brliu.rabbit.core.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class AsyncBaseQueue {

    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAysnc = new ThreadPoolExecutor(
            THREAD_SIZE, THREAD_SIZE * 2,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("rabbitmq_client_async_sender");
                return thread;
            },
            (runnable, executor) -> log.error("send message failed , async queue has rejected. runnable : {} , executor : {}", runnable, executor)
    );

    public static void submit(Runnable runnable) {
        senderAysnc.submit(runnable);
    }
}
