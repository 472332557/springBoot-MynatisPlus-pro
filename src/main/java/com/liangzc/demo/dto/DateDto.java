package com.liangzc.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: ligang
 * @Date: 2023/8/3 10:49
 * @Description:
 */
@Data
public class DateDto {

    private Long projectId;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date receivableMonth;
}
