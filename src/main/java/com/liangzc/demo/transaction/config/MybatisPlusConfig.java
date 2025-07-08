package com.liangzc.demo.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: liangzc
 * @Date: 2023/9/13 10:35
 * @Description:
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 自定义批量插入 SQL 注入器
     */
    @Bean
    public InsertBatchSqlInjector insertBatchSqlInjector() {
        return new InsertBatchSqlInjector();
    }
}
