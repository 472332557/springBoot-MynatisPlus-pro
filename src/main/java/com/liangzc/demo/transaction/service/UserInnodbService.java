package com.liangzc.demo.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboottest.transaction.model.UserInnodb;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:27
 * @Description:
 */
public interface UserInnodbService extends IService<UserInnodb> {
    List<UserInnodb> selectList();
}
