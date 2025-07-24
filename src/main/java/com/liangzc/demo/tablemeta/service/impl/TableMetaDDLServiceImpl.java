package com.liangzc.demo.tablemeta.service.impl;

import com.liangzc.demo.tablemeta.mapper.TableMetaDDLMapper;
import com.liangzc.demo.tablemeta.model.vo.TableColumnVO;
import com.liangzc.demo.tablemeta.service.TableMetaDDLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TableMetaDDLServiceImpl implements TableMetaDDLService {

    @Autowired
    private TableMetaDDLMapper tableMetaDDLMapper;

    @Override
    public List<TableColumnVO> getTableColumns(String dbName, String tableName) {
        List<Map<String, Object>> columns = tableMetaDDLMapper.getTableColumns(dbName, tableName);
        List<TableColumnVO> result = new ArrayList<>();
        for (Map<String, Object> col : columns) {
            TableColumnVO vo = new TableColumnVO();
            vo.setColumnName((String) col.get("COLUMN_NAME"));
            vo.setColumnType((String) col.get("COLUMN_TYPE"));
            vo.setColumnComment((String) col.get("COLUMN_COMMENT"));
            vo.setIsNullable((String) col.get("IS_NULLABLE"));
            vo.setColumnKey((String) col.get("COLUMN_KEY"));
            vo.setExtra((String) col.get("EXTRA"));
            result.add(vo);
        }
        return result;
    }

    @Override
    public boolean tableExists(String dbName, String tableName) {
        return tableMetaDDLMapper.tableExists(dbName, tableName) > 0;
    }

    @Override
    public boolean columnExists(String dbName, String tableName, String columnName) {
        return tableMetaDDLMapper.columnExists(dbName, tableName, columnName) > 0;
    }

    private String buildCreateTableSql(String tableName, List<TableColumnVO> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `").append(tableName).append("` (");
        for (int i = 0; i < columns.size(); i++) {
            TableColumnVO col = columns.get(i);
            sb.append("`").append(col.getColumnName()).append("` ")
              .append(col.getColumnType()).append(" ");
            if ("NO".equals(col.getIsNullable())) {
                sb.append("NOT NULL ");
            }
            if (col.getColumnKey() != null && "PRI".equals(col.getColumnKey())) {
                sb.append("PRIMARY KEY ");
            }
            if (col.getExtra() != null && !col.getExtra().isEmpty()) {
                sb.append(col.getExtra()).append(" ");
            }
            sb.append("COMMENT '").append(col.getColumnComment()).append("'");
            if (i < columns.size() - 1) sb.append(", ");
        }
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        return sb.toString();
    }

    private String buildAddColumnSql(String tableName, TableColumnVO col) {
        return String.format("ALTER TABLE `%s` ADD COLUMN `%s` %s %s %s COMMENT '%s';",
                tableName,
                col.getColumnName(),
                col.getColumnType(),
                "NO".equals(col.getIsNullable()) ? "NOT NULL" : "",
                col.getExtra() != null ? col.getExtra() : "",
                col.getColumnComment());
    }

    private String buildDropColumnSql(String tableName, String columnName) {
        return String.format("ALTER TABLE `%s` DROP COLUMN `%s`;", tableName, columnName);
    }
    
    private String buildModifyColumnSql(String tableName, String columnName, TableColumnVO newColumn) {
        return String.format("ALTER TABLE `%s` CHANGE COLUMN `%s` `%s` %s %s %s COMMENT '%s';",
                tableName,
                columnName,
                newColumn.getColumnName(),
                newColumn.getColumnType(),
                "NO".equals(newColumn.getIsNullable()) ? "NOT NULL" : "NULL",
                newColumn.getExtra() != null ? newColumn.getExtra() : "",
                newColumn.getColumnComment());
    }

    @Transactional
    public void executeDDL(String ddl) {
        tableMetaDDLMapper.executeDDL(ddl);
    }

    @Override
    public void createTable(String dbName, String tableName, List<TableColumnVO> columns) {
        if (tableExists(dbName, tableName)) {
            throw new RuntimeException("表已存在");
        }
        String ddl = buildCreateTableSql(tableName, columns);
        executeDDL(ddl);
    }

    @Override
    public void addColumn(String dbName, String tableName, TableColumnVO col) {
        if (!tableExists(dbName, tableName)) {
            throw new RuntimeException("表不存在");
        }
        if (columnExists(dbName, tableName, col.getColumnName())) {
            throw new RuntimeException("字段已存在");
        }
        String ddl = buildAddColumnSql(tableName, col);
        executeDDL(ddl);
    }

    @Override
    public void dropColumn(String dbName, String tableName, String columnName) {
        if (!tableExists(dbName, tableName)) {
            throw new RuntimeException("表不存在");
        }
        if (!columnExists(dbName, tableName, columnName)) {
            throw new RuntimeException("字段不存在");
        }
        String ddl = buildDropColumnSql(tableName, columnName);
        executeDDL(ddl);
    }
    
    @Override
    public void modifyColumn(String dbName, String tableName, String columnName, TableColumnVO newColumn) {
        if (!tableExists(dbName, tableName)) {
            throw new RuntimeException("表不存在");
        }
        if (!columnExists(dbName, tableName, columnName)) {
            throw new RuntimeException("字段不存在");
        }
        String ddl = buildModifyColumnSql(tableName, columnName, newColumn);
        executeDDL(ddl);
    }
}