package com.liangzc.demo.tablemeta.service;

import com.liangzc.demo.tablemeta.model.po.FieldInfo;
import java.util.List;

public interface TableMetaService {

    boolean tableExists(String tableName);

    boolean columnExists(String tableName, String columnName);

    List<FieldInfo> getTableFields(String tableName);

    void createTable(String tableName, List<FieldInfo> fields, String tableComment);

    void addColumn(String tableName, FieldInfo field);

    void dropColumn(String tableName, String columnName);
}
