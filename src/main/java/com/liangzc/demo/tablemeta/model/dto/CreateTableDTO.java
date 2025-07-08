package com.liangzc.demo.tablemeta.model.dto;

import com.liangzc.demo.tablemeta.model.po.FieldInfo;
import lombok.Data;

import java.util.List;

@Data
public class CreateTableDTO {
    private String tableName;

    private List<FieldInfo> fields;

    private String tableComment;
}