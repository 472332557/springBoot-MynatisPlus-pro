package com.liangzc.demo.tablemeta.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface TableMetaDDLMapper {
    @Select("SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT, IS_NULLABLE, COLUMN_KEY, EXTRA " +
            "FROM information_schema.COLUMNS " +
            "WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName} " +
            "ORDER BY ORDINAL_POSITION")
    List<Map<String, Object>> getTableColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);

    @Select("SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName}")
    int tableExists(@Param("dbName") String dbName, @Param("tableName") String tableName);

    @Select("SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName} AND COLUMN_NAME = #{columnName}")
    int columnExists(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("columnName") String columnName);

    @Update("${ddl}")
    void executeDDL(@Param("ddl") String ddl);
} 