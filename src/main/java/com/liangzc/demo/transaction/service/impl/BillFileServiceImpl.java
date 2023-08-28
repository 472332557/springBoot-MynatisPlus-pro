package com.liangzc.demo.transaction.service.impl;

import com.liangzc.demo.transaction.dao.BillFileMapper;
import com.liangzc.demo.transaction.service.BillFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Auther: liangzc
 * @Date: 2023/8/28 13:44
 * @Description:
 */
@Service
public class BillFileServiceImpl implements BillFileService {

    @Resource
    private BillFileMapper billFileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBillFile() {

    }
}
