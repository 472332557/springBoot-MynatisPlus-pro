package com.liangzc.demo.transaction.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: liangzc
 * @Date: 2023/9/13 10:37
 * @Description:
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    // 批量插入
    int insertBatchSomeColumn(@Param("list") List<T> batchList);

    /**
     * 批量更新（mysql）
     * @param entityList
     * @return
     */
    Integer updateBatchSomeColumn(@Param("list")List<T> entityList);


}
