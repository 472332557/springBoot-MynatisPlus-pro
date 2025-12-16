package com.liangzc.redis;

import com.alibaba.fastjson2.JSON;
import com.liangzc.demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class RedisExample {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;


    /**
     * Set：抽奖
     */
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

    /**
     * Set：交集、差集、并集
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void redissonSet2() throws ExecutionException, InterruptedException {
        RSet<Object> rSet = redissonClient.getSet("team1");
        List<String> list = Arrays.asList("提莫", "波比", "小法", "兰博","飞机","杰斯");
        rSet.addAll(list);
        log.info("添加成功：{}", rSet.size());

        RSet<Object> rSet1 = redissonClient.getSet("team2");
        List<String> list1 = Arrays.asList("诺手", "德莱文", "狮子狗","杰斯","小法");
        rSet1.addAll(list1);
        log.info("添加成功：{}", rSet1.size());

        RFuture<Set<Object>> team2 = rSet.readIntersectionAsync("team2");
        log.info("共同关注的人：{}", team2.get());

        RFuture<Set<Object>> team1 = rSet1.readDiffAsync("team1");
        log.info("team1可能认识的人：{}", team1.get());

        RFuture<Set<Object>> team3 = rSet1.readUnionAsync("team1");
        log.info("总的合并的人：{}", team3.get());


    }

    /**
     * List：队列/栈 ，先进先出，先进后出
     */
    @Test
    public void redissonList(){
        RDeque<Object> commentList = redissonClient.getDeque("comment_list");
        // LPUSH
        commentList.addFirst("Monday");
        commentList.addFirst("Tuesday");
        commentList.addFirst("Wednesday");
        commentList.addFirst("Thursday");
        commentList.addFirst("Friday");

        log.info("按时间先进先出！");
        while (commentList.size() > 0) {
            //先进先出，RPOP
            Object last = commentList.pollLast();
            //先进后出，LPOP
//            Object firstElement = commentList.pollFirst();
            log.info("取出的元素: {}", last);
        }
    }

    /**
     * List：阻塞获取
     */
    @Test
    public void redissonListBlock(){
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque("block_list");
        blockingDeque.add("Monday");
        try {
            while ( true){
                System.out.println("阻塞获取："+blockingDeque.poll(1000, TimeUnit.SECONDS));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

    }

}
