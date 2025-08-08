package com.liangzc.juc;

import com.liangzc.demo.DemoApplication;
import com.liangzc.demo.common.service.DelayedTaskService;
import com.liangzc.demo.rec.model.po.Receivable;
import com.liangzc.demo.rec.service.IReceivableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class ThreadPoolTest {

    @Resource
    private DelayedTaskService delayedTaskService;

    @Resource
    private IReceivableService receivableService;

    @Test
    public void delayThreadTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(()->{
            log.info("开始执行任务");
            try {
                log.info("睡眠5秒");
                TimeUnit.SECONDS.sleep(5);
                log.info("任务执行中。。。");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("任务结束");
        });
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(10);
        log.info("结束");
    }

    @Test
    public void delayThreadTest2() throws InterruptedException {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        log.info("延迟5秒后执行任务");
    scheduledExecutorService.schedule(() -> {
        try {
            log.info("任务执行中。。。");
            TimeUnit.SECONDS.sleep(2); // 模拟任务执行时间
            log.info("任务结束");
        } catch (InterruptedException e) {
            log.error("任务被中断", e);
            Thread.currentThread().interrupt();
        }
    }, 5, TimeUnit.SECONDS);

    // 关闭线程池
    scheduledExecutorService.shutdown();
    try {
        // 等待所有任务执行完毕
        if (!scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduledExecutorService.shutdownNow();
        }
    } catch (InterruptedException e) {
        scheduledExecutorService.shutdownNow();
        Thread.currentThread().interrupt();
    }
    log.info("主线程结束");
    }

    @Test
    public void delayThreadTest3() throws InterruptedException {
        log.info("延迟5秒后执行任务");
        delayedTaskService.scheduleTask(() -> {
            log.info("任务执行中。。。");
            List<Receivable> list = receivableService.list();
            for (Receivable receivable : list) {
                log.info("{}",receivable);
            }
            log.info("任务结束");
        }, 5, TimeUnit.SECONDS);
    }
}