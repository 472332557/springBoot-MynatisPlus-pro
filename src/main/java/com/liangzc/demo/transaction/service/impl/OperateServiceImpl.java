package com.liangzc.demo.transaction.service.impl;

import com.example.springboottest.transaction.model.PersonTest;
import com.example.springboottest.transaction.model.UserInnodb;
import com.example.springboottest.transaction.service.OperateService;
import com.example.springboottest.transaction.service.PersonTestService;
import com.example.springboottest.transaction.service.UserInnodbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 14:31
 * @Description:
 */
@Service
@Slf4j
public class OperateServiceImpl implements OperateService {

    @Autowired
    private PersonTestService personTestService;

    @Autowired
    private UserInnodbService userInnodbService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSomething() {
        //另起一个事务先保存数据
        personTestService.insertObj();
//        PersonTest personTest = new PersonTest();
//        personTest.setAge(10);
//        personTest.setGender(0);
//        personTest.setName("小魔女");
//        personTestService.save(personTest);

        UserInnodb userInnodb = new UserInnodb();
        userInnodb.setName("张晓");
        userInnodb.setGender(Boolean.FALSE);
        userInnodb.setPhone("13265263203");
        userInnodbService.save(userInnodb);
//        System.out.println(6 / 0);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertNoTrans() {
        UserInnodb userInnodb = new UserInnodb();
        userInnodb.setName("张晓333");
        userInnodb.setGender(Boolean.FALSE);
        userInnodb.setPhone("13265263304");
        userInnodbService.save(userInnodb);


        insertObj();

        System.out.println(6 / 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertNoSupport() {

        try {
            personTestService.insertNoSupport();
        }catch (Exception e){
            log.error("报错啦",e);
            throw new RuntimeException("报错啦",e);
        }

        UserInnodb userInnodb = new UserInnodb();
        userInnodb.setName("张晓777");
        userInnodb.setGender(Boolean.FALSE);
        userInnodb.setPhone("13265260821");
        userInnodbService.save(userInnodb);
    }

    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void insertObj() {
        PersonTest personTest = new PersonTest();
        personTest.setAge(13);
        personTest.setGender(0);
        personTest.setName("小魔女1111");
        personTestService.save(personTest);

    }
}
