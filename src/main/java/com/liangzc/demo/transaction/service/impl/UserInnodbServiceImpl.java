package com.liangzc.demo.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzc.demo.transaction.mapper.PersonTestMapper;
import com.liangzc.demo.transaction.mapper.UserInnodbMapper;
import com.liangzc.demo.transaction.model.UserInnodb;
import com.liangzc.demo.transaction.service.UserInnodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:27
 * @Description:
 */
@Service
public class UserInnodbServiceImpl extends ServiceImpl<UserInnodbMapper, UserInnodb> implements UserInnodbService {

    @Autowired
    private UserInnodbMapper userInnodbMapper;

    @Autowired
    private PersonTestMapper personTestMapper;

    @Override
    public List<UserInnodb> selectList() {
        return userInnodbMapper.selectList(null);
    }

    private String method2(String param){
        System.out.println("这是一个private访问类型的方法!"+param);
        return "这是一个private访问类型的方法!"+param;
    }



    private String method3(String param){
        System.out.println("这是一个private访问类型的方法!"+param);
        return "这是一个private访问类型的方法!"+param;
    }

    private void method5(boolean isCheck,Long num,UserInnodb userInnodb){
        if (isCheck){
            System.out.println("SUCCESS");
        }else {
            System.out.println("FALSE");
        }
    }
}
