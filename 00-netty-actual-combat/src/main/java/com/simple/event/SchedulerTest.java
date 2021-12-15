package com.simple.event;


import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 11:54
 **/
public class SchedulerTest {

    public static void main(String[] args) throws InterruptedException {
        testJDKImpl();
    }

    public static void testJDKImpl() throws InterruptedException {
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = executor.schedule(() -> System.out.println("10 seconds later"), 10, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);
        executor.shutdown();
    }

    public static void testNettyImpl() throws InterruptedException {
        Channel ch = null;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(() -> System.out.println("Run every 10 seconds"), 10, 10, TimeUnit.SECONDS);
        // Some other code that runs...
        future.cancel(false);
    }
}
