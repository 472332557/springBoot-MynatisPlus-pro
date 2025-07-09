package com.liangzc.demo.tablemeta.model.vo;

public class TableColumnVO {
    private String columnName;
    private String columnType;
    private String columnComment;
    private String isNullable;
    private String columnKey;
    private String extra;

    // getter/setter
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    public String getColumnType() { return columnType; }
    public void setColumnType(String columnType) { this.columnType = columnType; }
    public String getColumnComment() { return columnComment; }
    public void setColumnComment(String columnComment) { this.columnComment = columnComment; }
    public String getIsNullable() { return isNullable; }
    public void setIsNullable(String isNullable) { this.isNullable = isNullable; }
    public String getColumnKey() { return columnKey; }
    public void setColumnKey(String columnKey) { this.columnKey = columnKey; }
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
} 