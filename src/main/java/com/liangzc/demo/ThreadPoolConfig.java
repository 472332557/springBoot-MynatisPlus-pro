package com.liangzc.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置
 */
@EnableAsync
@Configuration
@Slf4j
public class ThreadPoolConfig {


    /**
     * saas化线程池的Bean名称
     */
    public static final String SAAS_THREAD_POOL_EXECUTOR = "saasThreadPoolExecutor";

    /**
     * 普通线程池的Bean名称
     */
    public static final String NORMAL_THREAD_POOL_EXECUTOR = "normalThreadExecutor";
    
    /**
     * 延迟任务线程池的Bean名称
     */
    public static final String SCHEDULED_THREAD_POOL_EXECUTOR = "scheduledThreadPoolExecutor";


    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 线程池所使用的缓冲队列大小的默认值
     */
    private static final int WORK_QUEUE_CAPACITY = 1024;


    /**
     * 配置saas线程池
     *
     * @return
     */
    @Bean(value = SAAS_THREAD_POOL_EXECUTOR, destroyMethod = "shutdown")
    public ThreadPoolExecutor saasThreadPoolExecutor() {
        /**
         * 使用2倍cpu核心数
         */
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        int maximumPoolSize = corePoolSize * 2;
        AtomicInteger saasThreadNumber = new AtomicInteger(0);
        /**
         * 使用2倍cpu核心数
         */
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(WORK_QUEUE_CAPACITY),
                runnable -> new Thread(runnable, SAAS_THREAD_POOL_EXECUTOR + "-" + saasThreadNumber.getAndIncrement())
        );
    }


    /**
     * 配置普通线程池
     *
     * @return
     */
    @Bean(value = NORMAL_THREAD_POOL_EXECUTOR, destroyMethod = "shutdown")
    public ExecutorService normalThreadExecutor() {
        /**
         * 使用2倍cpu核心数
         */
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        int maximumPoolSize = corePoolSize * 2;
        String threadName = "asyncThread";

        AtomicInteger threadNumber = new AtomicInteger(0);
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(WORK_QUEUE_CAPACITY),
                runnable -> new Thread(runnable, threadName + "-" + threadNumber.getAndIncrement())
        );
    }
    
    /**
     * 配置支持延迟执行任务的线程池
     *
     * @return
     */
    @Bean(value = SCHEDULED_THREAD_POOL_EXECUTOR, destroyMethod = "shutdown")
    public ScheduledExecutorService scheduledThreadPoolExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        String threadName = "scheduledThread";
        
        AtomicInteger threadNumber = new AtomicInteger(0);
        return new ScheduledThreadPoolExecutor(
                corePoolSize,
                runnable -> new Thread(runnable, threadName + "-" + threadNumber.getAndIncrement())
        );
    }

}