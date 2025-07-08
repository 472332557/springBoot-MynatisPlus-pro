package com.liangzc.demo;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.liangzc.demo.rec.model.po.Receivable;
import com.liangzc.demo.rec.service.IReceivableService;
import com.liangzc.demo.springContext.SpringContextUtil;
import com.liangzc.demo.transaction.mapper.PersonTestMapper;
import com.liangzc.demo.transaction.model.PersonTest;
import com.liangzc.demo.transaction.model.UserInnodb;
import com.liangzc.demo.transaction.service.PersonTestService;
import com.liangzc.demo.transaction.service.UserInnodbService;
import com.liangzc.demo.transaction.service.impl.UserInnodbServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Autowired
    private PersonTestService personTestService;

    @Autowired
    private PersonTestMapper personTestMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserInnodbService userInnodbService;

    @Resource
    private IReceivableService receivableService;

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


    @Test
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void catchTest(){

        PersonTest personTest = new PersonTest();
        personTest.setAge(100);
        personTest.setGender(1);
        personTest.setName("老者");
        int a = 18;
        int b = 0;
        try {
            personTestService.save(personTest);
            int c = a / b;
        }catch (Exception e) {
            log.error("异常了：{}",e.getMessage());
            personTestService.insertObj();
        }
    }

    @Test
    public void tableModelTest(){
        List<Receivable> receivables = receivableService.getBaseMapper().selectList(null);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(Receivable.class);
        log.info("tableInfo:{}",tableInfo);
        tableInfo.getFieldList().forEach(fieldInfo -> {
            Field field = fieldInfo.getField();
            log.info("field:{}",field.getName());
            
            // 获取字段类型
            Class<?> fieldType = field.getType();
            log.info("fieldType:{}", fieldType.getName());
            log.info("fieldType:{}", fieldType.getSimpleName().toLowerCase());
            
            // 如果jdbcType为null，可以手动映射Java类型到JDBC类型
            JdbcType jdbcType = fieldInfo.getJdbcType();
            log.info("jdbcType:{}", jdbcType != null ? jdbcType.name() : "UNKNOWN");
            
            String column = fieldInfo.getColumn();
            log.info("column:{}",column);
            log.info("--------------------------");
        });
    }

    @Test
    public void getMetaInfo() throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bop_charge?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UT&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", "root", "lzc2025666")) {
            DatabaseMetaData metaData = connection.getMetaData();
            try(ResultSet resultSet = metaData.getColumns(null, null, "rec_receivable", null)) {
                while (resultSet.next()){
                    log.info("columnName:{}",resultSet.getString("COLUMN_NAME"));
                    log.info("columnType:{}",resultSet.getString("TYPE_NAME"));
                    log.info("columnSize:{}",resultSet.getInt("COLUMN_SIZE"));
                    log.info("REMARKS:{}",resultSet.getString("REMARKS"));
                    log.info("--------------------------");
                }
            }
        }
    }
}
