package com.liangzc.demo;

import com.liangzc.demo.transaction.dao.PersonTestMapper;
import com.liangzc.demo.transaction.model.PersonTest;
import com.liangzc.demo.transaction.service.PersonTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        for (int i = 0; i < 100000; i++) {
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





}
