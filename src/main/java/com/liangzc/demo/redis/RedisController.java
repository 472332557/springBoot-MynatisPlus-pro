package com.liangzc.demo.redis;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/redis")
@RestController
@Slf4j
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${query.count}")
    private Long count;

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @RequestMapping("/set/{id}/{value}")
    public String set(@PathVariable Long id, @PathVariable String value) {
        System.out.println("id:" + id + " value:" + value);
         Long increment = redisTemplate.opsForValue().increment(id);
        log.info("increment:" + increment);
        if (increment > count) {
            return "error,超过限定次数";
        }
//        redisTemplate.opsForValue().set(id, value);

        redisTemplate.opsForHash().put("user", id, value);
        return "success";
    }


}
