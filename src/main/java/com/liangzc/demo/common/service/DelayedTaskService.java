package com.liangzc.demo.common.service;

import com.liangzc.demo.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务服务示例
 * 演示如何使用线程池执行延迟任务
 */

@Slf4j
@Service
public class DelayedTaskService {


    @Resource(name = ThreadPoolConfig.SCHEDULED_THREAD_POOL_EXECUTOR)
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 延迟执行任务
     *
     * @param task  要执行的任务
     * @param delay 延迟时间
     * @param unit  时间单位
     */
    public void scheduleTask(Runnable task, long delay, TimeUnit unit) {
        log.info("提交延迟任务，将在 {} {} 后执行", delay, unit);
        scheduledExecutorService.schedule(task, delay, unit);
    }

    /**
     * 周期性执行任务，首次执行有延迟
     *
     * @param task         要执行的任务
     * @param initialDelay 首次执行的延迟时间
     * @param period       执行周期
     * @param unit         时间单位
     */
    public void schedulePeriodicTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
        log.info("提交周期性延迟任务，首次执行延迟 {} {}，之后每 {} {} 执行一次", initialDelay, unit, period, unit);
        scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * 周期性执行任务，每次执行完后等待指定时间再执行下一次
     *
     * @param task         要执行的任务
     * @param initialDelay 首次执行的延迟时间
     * @param delay        每次执行完成后到下次执行的延迟时间
     * @param unit         时间单位
     */
    public void scheduleFixedDelayTask(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        log.info("提交固定延迟周期性任务，首次执行延迟 {} {}，每次执行完成后延迟 {} {} 再执行下一次", initialDelay, unit, delay, unit);
        scheduledExecutorService.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }
    
    /**
     * 优雅关闭线程池
     * 该方法会在Spring容器关闭时自动调用
     */
//    @PreDestroy
//    public void shutdown() {
//        log.info("开始关闭延迟任务线程池");
//        scheduledExecutorService.shutdown();
//        try {
//            // 等待最多60秒让现有任务执行完毕
//            if (!scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS)) {
//                log.warn("延迟任务线程池未能在60秒内正常关闭，尝试强制关闭");
//                scheduledExecutorService.shutdownNow();
//
//                // 再等待最多60秒确保关闭完成
//                if (!scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS)) {
//                    log.error("延迟任务线程池未能正常关闭");
//                }
//            }
//        } catch (InterruptedException e) {
//            log.error("关闭延迟任务线程池时被中断", e);
//            scheduledExecutorService.shutdownNow();
//            Thread.currentThread().interrupt();
//        }
//        log.info("延迟任务线程池已关闭");
//    }
    
    /**
     * 检查线程池是否已关闭
     * @return true表示已关闭，false表示仍在运行
     */
    public boolean isShutdown() {
        return scheduledExecutorService.isShutdown();
    }
    
    /**
     * 检查线程池是否已完成终止
     * @return true表示已完成终止，false表示尚未完成或仍在运行
     */
    public boolean isTerminated() {
        return scheduledExecutorService.isTerminated();
    }
}