package com.liangzc.redis;

import com.alibaba.fastjson2.JSON;
import com.liangzc.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class RedisExample {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;


    @Test
    public void redissonSet(){
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
    }

    @Test
    public void redissonList(){
        RDeque<Object> commentList = redissonClient.getDeque("comment_list");
        commentList.addFirst("Monday");
        commentList.addFirst("Tuesday");
        commentList.addFirst("Wednesday");
        commentList.addFirst("Thursday");
        commentList.addFirst("Friday");

        log.info("按时间先后获取！");
        while (commentList.size() > 0) {
            commentList.poll();
            log.info("{}", commentList.pollLastAsync());
        }
    }


}
