package com.liangzc.demo.redis;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequestMapping("/redis")
@RestController
@Slf4j
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${query.count}")
    private Long count;

    @Resource
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

    /**
     * redisson测试，测试set集合的抽奖
     * @return
     */
    @GetMapping("/redisson/set")
    public String redissontest() {
        List<String> list = Arrays.asList("小明", "小李", "乐乐", "波比", "提莫", "海龟", "小于", "小萨");
        redissonClient.getSet("user").clear();
        boolean user = redissonClient.getSet("user").addAll(list);
        if ( user){
            log.info("添加成功");
            log.info("开始抽奖");
            log.info("=========================可重复抽奖======================");
            Set<Object> user1 = redissonClient.getSet("user").random(3);
            log.info("三等奖中奖用户：{}", JSON.toJSONString(user1));
            Set<Object> user2 = redissonClient.getSet("user").random(2);
            log.info("二等奖中奖用户：{}", JSON.toJSONString(user2));
            Set<Object> user3 = redissonClient.getSet("user").random(1);
            log.info("一等奖中奖用户：{}", JSON.toJSONString(user3));

            log.info("=========================不可重复抽奖======================");
            Set<Object> user4 = redissonClient.getSet("user").removeRandom(3);
            log.info("三等奖中奖用户：{}", JSON.toJSONString(user4));
            Set<Object> user5 = redissonClient.getSet("user").removeRandom(2);
            log.info("二等奖中奖用户：{}", JSON.toJSONString(user5));
            Set<Object> user6 = redissonClient.getSet("user").removeRandom(1);
            log.info("一等奖中奖用户：{}", JSON.toJSONString(user6));
        }
        return "SUCCESS";
    }

    @GetMapping("/redisson/list")
    public String redissonList() {
        redissonClient.getList("comment_list").addAsync("Monday");
        redissonClient.getList("comment_list").addAsync("Tuesday");
        redissonClient.getList("comment_list").addAsync("Wednesday");
        redissonClient.getList("comment_list").addAsync("Thursday");
        redissonClient.getList("comment_list").addAsync("Friday");

        log.info("按时间先后获取！");

        return "SUCCESS";
    }

}
