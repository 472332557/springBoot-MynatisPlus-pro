package com.liangzc.demo.transaction.mapper;

import com.liangzc.demo.transaction.config.MyBaseMapper;
import com.liangzc.demo.transaction.model.PersonTest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonTestMapper extends MyBaseMapper<PersonTest> {

    void updateBatchById(@Param("PersonTestList") List<PersonTest> list);
}