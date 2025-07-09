package com.liangzc.demo.tablemeta.controller;

import com.liangzc.demo.tablemeta.model.vo.TableColumnVO;
import com.liangzc.demo.tablemeta.service.TableMetaDDLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tablemeta/ddl")
public class TableMetaDDLController {

    @Autowired
    private TableMetaDDLService tableMetaDDLService;

    // 获取表字段元数据
    @GetMapping("/columns")
    public List<TableColumnVO> getTableColumns(
            @RequestParam String dbName,
            @RequestParam String tableName) {
        return tableMetaDDLService.getTableColumns(dbName, tableName);
    }

    // 创建新表
    @PostMapping("/createTable")
    public String createTable(
            @RequestParam String dbName,
            @RequestParam String newTableName,
            @RequestBody List<TableColumnVO> columns) {
        tableMetaDDLService.createTable(dbName, newTableName, columns);
        return "创建成功";
    }

    // 添加字段
    @PostMapping("/addColumn")
    public String addColumn(
            @RequestParam String dbName,
            @RequestParam String tableName,
            @RequestBody TableColumnVO column) {
        tableMetaDDLService.addColumn(dbName, tableName, column);
        return "添加成功";
    }

    // 删除字段
    @PostMapping("/dropColumn")
    public String dropColumn(
            @RequestParam String dbName,
            @RequestParam String tableName,
            @RequestParam String columnName) {
        tableMetaDDLService.dropColumn(dbName, tableName, columnName);
        return "删除成功";
    }
} 