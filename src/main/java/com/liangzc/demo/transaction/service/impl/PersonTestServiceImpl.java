package com.liangzc.demo.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzc.demo.transaction.mapper.PersonTestMapper;
import com.liangzc.demo.transaction.model.PersonTest;
import com.liangzc.demo.transaction.service.PersonTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:27
 * @Description:
 */
@Service
public class PersonTestServiceImpl extends ServiceImpl<PersonTestMapper,PersonTest> implements PersonTestService {

    @Autowired
    private PersonTestMapper personTestMapper;

    @Override
    public List<PersonTest> selectList() {
        return personTestMapper.selectList(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    //另起一个事务处理
    public void insertObj() {
        PersonTest personTest = new PersonTest();
        personTest.setAge(10);
        personTest.setGender(0);
        personTest.setName("小魔女");
        personTestMapper.insert(personTest);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertNoSupport() {
        PersonTest personTest = new PersonTest();
        personTest.setAge(20);
        personTest.setGender(0);
        personTest.setName("小魔女202308211602");
        personTestMapper.insert(personTest);

        System.out.println(6 / 0);
    }

}
