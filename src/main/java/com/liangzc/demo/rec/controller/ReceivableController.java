package com.liangzc.demo.rec.controller;

import com.alibaba.fastjson2.JSON;
import com.liangzc.demo.rec.service.IReceivableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 应收明细表 前端控制器
 * </p>
 *
 * @author liangzc
 * @since 2025-07-08
 */
@RestController
@RequestMapping("/receivable")
public class ReceivableController {

    @Resource
    private IReceivableService receivableService;


    @RequestMapping(path = "/query",method = RequestMethod.GET)
    public String query(){
        String result = receivableService.selectList();
        return result;
    }
}
