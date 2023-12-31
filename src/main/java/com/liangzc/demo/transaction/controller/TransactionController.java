package com.liangzc.demo.transaction.controller;

import com.alibaba.fastjson2.JSON;
import com.liangzc.demo.transaction.model.PersonTest;
import com.liangzc.demo.transaction.model.UserInnodb;
import com.liangzc.demo.transaction.service.BillFileService;
import com.liangzc.demo.transaction.service.OperateService;
import com.liangzc.demo.transaction.service.PersonTestService;
import com.liangzc.demo.transaction.service.UserInnodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Auther: liangzc
 * @Date: 2023/8/15 11:33
 * @Description:
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private UserInnodbService userInnodbService;

    @Autowired
    private PersonTestService personTestService;

    @Autowired
    private OperateService operateService;

    @Resource
    private BillFileService billFileService;



    @GetMapping("/query")
    public String query(){

        List<UserInnodb> userInnodbList = userInnodbService.selectList();
        List<PersonTest> personTestList = personTestService.selectList();

        userInnodbList.stream().forEach(userInnodb -> System.out.println(userInnodb));

        personTestList.stream().forEach(personTest -> System.out.println(personTest));

        return JSON.toJSONString(userInnodbList).concat(JSON.toJSONString(personTestList));
    }

    @GetMapping("/queryLambda")
    public String queryLambda(){

        List<UserInnodb> list = userInnodbService.lambdaQuery().eq(UserInnodb::getGender, 0).list();
        List<UserInnodb> innodbList = list.stream().filter(e -> !e.getName().equals("张晓")).collect(Collectors.toList());
        return JSON.toJSONString(innodbList);
    }

    @GetMapping("/updateLambda")
    public void updateLambda(){

        boolean update = userInnodbService.lambdaUpdate().set(UserInnodb::getName, "蒋净杭111").eq(UserInnodb::getId, 10).update();
        System.out.println(update);
    }



    @GetMapping("/doUpdate")
    public void updateData(){
        operateService.updateSomething();
    }


    @GetMapping("/insertNoTrans")
    public void insertNoTrans(){
        operateService.insertNoTrans();
    }


    @GetMapping("/insertNoSupport")
    public void insertNoSupport(){
        operateService.insertNoSupport();
    }


    public void billFileOperate(){
        billFileService.saveBillFile();
    }

}
