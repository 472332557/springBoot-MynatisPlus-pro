package com.liangzc.demo.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzc.demo.transaction.model.PersonTest;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:27
 * @Description:
 */
public interface PersonTestService extends IService<PersonTest> {
    List<PersonTest> selectList();

    void insertObj();

    void insertNoSupport();

}
