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
}
