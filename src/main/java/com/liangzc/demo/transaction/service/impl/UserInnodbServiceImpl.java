package com.liangzc.demo.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboottest.transaction.dao.PersonTestMapper;
import com.example.springboottest.transaction.dao.UserInnodbMapper;
import com.example.springboottest.transaction.model.UserInnodb;
import com.example.springboottest.transaction.service.UserInnodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:27
 * @Description:
 */
@Service
public class UserInnodbServiceImpl extends ServiceImpl<UserInnodbMapper,UserInnodb> implements UserInnodbService {

    @Autowired
    private UserInnodbMapper userInnodbMapper;

    @Autowired
    private PersonTestMapper personTestMapper;

    @Override
    public List<UserInnodb> selectList() {
        return userInnodbMapper.selectList(null);
    }
}
