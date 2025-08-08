package com.liangzc.demo.rec.service.impl;

import com.alibaba.fastjson2.JSON;
import com.liangzc.demo.common.service.DelayedTaskService;
import com.liangzc.demo.rec.model.po.Receivable;
import com.liangzc.demo.rec.mapper.ReceivableMapper;
import com.liangzc.demo.rec.service.IReceivableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 应收明细表 服务实现类
 * </p>
 *
 * @author liangzc
 * @since 2025-07-08
 */
@Service
@Slf4j
public class ReceivableServiceImpl extends ServiceImpl<ReceivableMapper, Receivable> implements IReceivableService {

    @Resource
    private DelayedTaskService delayedTaskService;
    @Override
    public String selectList() {
        delayedTaskService.scheduleTask(() -> {
            log.info("开始执行定时任务");
            List<Receivable> list = this.lambdaQuery().list();
            list.forEach(receivable -> {
                log.info("receivable:{}", JSON.toJSONString(receivable));
            });
        }, 10, TimeUnit.SECONDS);
        return "SUCCESS";
    }

    @Override
    public String selectListPeriodic() {
        delayedTaskService.schedulePeriodicTask(() -> {
            log.info("开始执行定时任务");
            List<Receivable> list = this.lambdaQuery().list();
            list.forEach(receivable -> {
                log.info("receivable:{}", JSON.toJSONString(receivable));
            });
        }, 10, 5, TimeUnit.SECONDS);
        return "SUCCESS";
    }

    @Override
    public String selectListByCustom() {
        /**
         *  如果在生产环境中使用这种方式创建线程池，会存在以下几个问题：
         *  1、每次调用都创建新的线程池：每次方法被调用时都会创建一个新的线程池实例
         *  2、虽然调用了shutdown()，但这并不会立即关闭线程池，只是不再接受新任务，而方法每次被调用都会创建新的线程池，容易造成资源浪费
         *  3、没有统一管理：这些线程池不受 Spring 容器管理，无法在应用关闭时统一处理
         *  4、资源浪费：即使任务执行完毕，线程池仍然存在于内存中，直到被垃圾回收
         *
         *  使用Spring bean单例模式的方式管理线程池有如下好处：
         *  一次创建，多次复用：
         *      1、ThreadPoolConfig 中的 @Bean 方法只在 Spring 容器初始化时执行一次
         *      2、创建的线程池实例在整个应用生命周期中被所有需要的组件共享
         *  资源管理：
         *      1、Spring 容器负责管理线程池的生命周期
         *      2、通过 destroyMethod = "shutdown" 指定销毁方法，在容器关闭时会自动调用关闭线程池
         *  线程安全性：
         *      1、线程池本身是线程安全的，可以被多个组件并发使用
         *      2、不会出现多个线程池实例同时存在的情况
         */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(() -> {
            log.info("开始执行定时任务");
            List<Receivable> list = this.lambdaQuery().list();
            list.forEach(receivable -> {
                log.info("receivable:{}", JSON.toJSONString(receivable));
            });
        }, 10, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
        return "SUCCESS";
    }
}
