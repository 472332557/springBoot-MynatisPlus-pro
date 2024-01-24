package com.liangzc.demo;

import com.liangzc.demo.springContext.SpringContextUtil;
import com.liangzc.demo.transaction.dao.PersonTestMapper;
import com.liangzc.demo.transaction.model.PersonTest;
import com.liangzc.demo.transaction.model.UserInnodb;
import com.liangzc.demo.transaction.service.PersonTestService;
import com.liangzc.demo.transaction.service.UserInnodbService;
import com.liangzc.demo.transaction.service.impl.BillFileServiceImpl;
import com.liangzc.demo.transaction.service.impl.UserInnodbServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private PersonTestService personTestService;

    @Autowired
    private PersonTestMapper personTestMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserInnodbService userInnodbService;

    @Test
    void contextLoads() {

    }

    @Test
    public void between(){

        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2021, 1, 1);
        Period period = Period.between(date1, date2);
        int diff = period.getMonths() + period.getYears() * 12;
        System.out.println("两个日期之间相差 " + diff + " 个月");

    }


    @Test
    public void insertTest(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setAge(i);
            personTest.setGender(i);
            personTest.setName("测试"+i);
            personTestService.save(personTest);
        }
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }

    @Test
    public void insertBatchTest(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setAge(i);
            personTest.setGender(i);
            personTest.setName("测试"+i);
            list.add(personTest);
        }
        personTestService.saveBatch(list);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }

    @Test
    public void insertBatchSomeColumnTest(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
        for (int i = 401001; i < 411001; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setAge(i);
            personTest.setGender(i);
            personTest.setName("测试"+i);
            list.add(personTest);
        }
        personTestMapper.insertBatchSomeColumn(list);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }


    @Test
    public void batchUpdate(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
        for (int i = 400018; i < 410018; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setName("测试"+i);
            personTest.setAge(i);
            personTest.setId(i);
            list.add(personTest);
        }
        System.out.println("集合大小："+list.size());
        personTestMapper.updateBatchById(list);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }


    @Test
    public void batchUpdateMybatis(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
        for (int i = 410018; i < 420018; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setName("测试"+i);
            personTest.setAge(i);
            personTest.setId(i);
            list.add(personTest);
        }
        System.out.println("集合大小："+list.size());
        personTestService.updateBatchById(list,10000);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }


    @Test
    public void batchUpdateSomeColumns(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
            for (int i = 500018; i <= 501001; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setName("测试"+i);
            personTest.setAge(i);
            personTest.setId(i);
            list.add(personTest);
        }
        System.out.println("集合大小："+list.size());
        personTestMapper.updateBatchSomeColumn(list);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }

    @Test
    public void subTest(){
        long start = System.currentTimeMillis();
        List<PersonTest> list = new ArrayList<>();
        for (int i = 440018; i < 460018; i++) {
            PersonTest personTest = new PersonTest();
            personTest.setName("测试"+i);
            personTest.setAge(i);
            personTest.setId(i);
            list.add(personTest);
        }
        System.out.println("集合大小："+list.size());
        List<PersonTest> list1 = list.subList(0, 1);
        System.out.println(list1);
        System.out.println("--------------------耗时："+(System.currentTimeMillis() - start));
    }


    @Test
    public void redisNx(){
        Long key = 1213132132321L;
        try{
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, key);
            if (ifAbsent){
                System.out.println("---------加锁成功！");
            }
            Boolean ifAbsent1 = redisTemplate.opsForValue().setIfAbsent(key, "新的值");
            System.out.println(ifAbsent1);
        }finally {
            redisTemplate.delete(key);
        }
    }

    @Test
    public void springContext() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        UserInnodbServiceImpl userInnodbService = SpringContextUtil.getBean(UserInnodbServiceImpl.class);
        System.out.println(userInnodbService);
        System.out.println(userInnodbService.selectList());

        System.out.println("获取类路径："+this.getClass());

        Method method2 = UserInnodbServiceImpl.class.getDeclaredMethod("method2", String.class);
        method2.setAccessible(true);

        for (int i = 0; i <10 ; i++) {
            Object invoke = method2.invoke(userInnodbService, "反射私有方法调用第"+i+"次");
            System.out.println(invoke);
        }


        Method method5 = UserInnodbServiceImpl.class.getDeclaredMethod("method5", boolean.class,Long.class,UserInnodb.class,String.class);
        method5.setAccessible(true);
        method5.invoke(userInnodbService, false, null,new UserInnodb());


    }


    @Test
    public void updateLambda(){
        boolean update = userInnodbService.lambdaUpdate().set(UserInnodb::getName, "蒋净杭").eq(UserInnodb::getId, 10).update();
        System.out.println(update);
    }


    @Test
    public void queryLambda(){
        List<UserInnodb> list = userInnodbService.lambdaQuery().gt(UserInnodb::getId, 10).list();
        System.out.println(list);
    }
}
