package com.liangzc.demo.tablemeta.controller;

import com.liangzc.demo.tablemeta.model.dto.CreateTableDTO;
import com.liangzc.demo.tablemeta.model.po.FieldInfo;
import com.liangzc.demo.tablemeta.service.TableMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableMetaController {

    @Autowired
    private TableMetaService tableMetaService;

    @GetMapping("/fields")
    public List<FieldInfo> getTableFields(@RequestParam String tableName) {
        return tableMetaService.getTableFields(tableName);
    }

    @PostMapping("/create")
    public String createTable(@RequestBody CreateTableDTO request) {
        if (tableMetaService.tableExists(request.getTableName())) {
            return "表已存在";
        }
        tableMetaService.createTable(request.getTableName(), request.getFields(), request.getTableComment());
        return "创建成功";
    }

    @PostMapping("/addColumn")
    public String addColumn(@RequestParam String tableName, @RequestBody FieldInfo field) {
        if (!tableMetaService.tableExists(tableName)) {
            return "表不存在";
        }
        if (tableMetaService.columnExists(tableName, field.getName())) {
            return "字段已存在";
        }
        tableMetaService.addColumn(tableName, field);
        return "添加成功";
    }

    @PostMapping("/dropColumn")
    public String dropColumn(@RequestParam String tableName, @RequestParam String columnName) {
        if (!tableMetaService.tableExists(tableName)) {
            return "表不存在";
        }
        if (!tableMetaService.columnExists(tableName, columnName)) {
            return "字段不存在";
        }
        tableMetaService.dropColumn(tableName, columnName);
        return "删除成功";
    }
}
