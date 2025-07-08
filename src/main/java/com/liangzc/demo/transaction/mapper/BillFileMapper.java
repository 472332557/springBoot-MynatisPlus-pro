package com.liangzc.demo.transaction.mapper;

import com.liangzc.demo.transaction.model.BillFile;
import com.liangzc.demo.transaction.model.BillFileCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface BillFileMapper {
    int countByExample(BillFileCriteria example);

    int deleteByExample(BillFileCriteria example);

    int deleteByPrimaryKey(Long fileId);

    int insert(BillFile record);

    int insertSelective(BillFile record);

    List<BillFile> selectByExampleWithRowbounds(BillFileCriteria example, RowBounds rowBounds);

    List<BillFile> selectByExample(BillFileCriteria example);

    BillFile selectByPrimaryKey(Long fileId);

    int updateByExampleSelective(@Param("record") BillFile record, @Param("example") BillFileCriteria example);

    int updateByExample(@Param("record") BillFile record, @Param("example") BillFileCriteria example);

    int updateByPrimaryKeySelective(BillFile record);

    int updateByPrimaryKey(BillFile record);
}