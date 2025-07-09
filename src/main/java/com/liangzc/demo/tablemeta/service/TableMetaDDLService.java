package com.liangzc.demo.tablemeta.service;

import com.liangzc.demo.tablemeta.model.vo.TableColumnVO;
import java.util.List;

public interface TableMetaDDLService {
    List<TableColumnVO> getTableColumns(String dbName, String tableName);
    boolean tableExists(String dbName, String tableName);
    boolean columnExists(String dbName, String tableName, String columnName);
    void createTable(String dbName, String tableName, List<TableColumnVO> columns);
    void addColumn(String dbName, String tableName, TableColumnVO col);
    void dropColumn(String dbName, String tableName, String columnName);
} 