package com.liangzc.demo.tablemeta.service.impl;

import com.liangzc.demo.tablemeta.model.po.FieldInfo;
import com.liangzc.demo.tablemeta.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableMetaServiceImpl implements TableMetaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String dbName = "bop_charge"; // 替换为你的数据库名

    @Override
    public boolean tableExists(String tableName) {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = ? AND table_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{dbName, tableName}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public boolean columnExists(String tableName, String columnName) {
        String sql = "SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = ? AND table_name = ? AND column_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{dbName, tableName, columnName}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public List<FieldInfo> getTableFields(String tableName) {
        String sql = "SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT " +
                "FROM information_schema.columns " +
                "WHERE table_schema = ? AND table_name = ?";
        return jdbcTemplate.query(sql, new Object[]{dbName, tableName}, (rs, rowNum) -> {
            FieldInfo field = new FieldInfo();
            field.setName(rs.getString("COLUMN_NAME"));
            field.setType(rs.getString("COLUMN_TYPE"));
            field.setComment(rs.getString("COLUMN_COMMENT"));
            return field;
        });
    }

    @Override
    public void createTable(String tableName, List<FieldInfo> fields, String tableComment) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" (");
        for (FieldInfo field : fields) {
            sb.append(field.getName()).append(" ")
                    .append(field.getType()).append(" COMMENT '")
                    .append(field.getComment()).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") COMMENT='").append(tableComment).append("'");
        jdbcTemplate.execute(sb.toString());
    }

    @Override
    public void addColumn(String tableName, FieldInfo field) {
        String sql = String.format(
                "ALTER TABLE %s ADD COLUMN %s %s COMMENT '%s'",
                tableName, field.getName(), field.getType(), field.getComment()
        );
        jdbcTemplate.execute(sql);
    }

    @Override
    public void dropColumn(String tableName, String columnName) {
        String sql = String.format(
                "ALTER TABLE %s DROP COLUMN %s",
                tableName, columnName
        );
        jdbcTemplate.execute(sql);
    }
}
