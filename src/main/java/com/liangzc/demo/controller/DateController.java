package com.liangzc.demo.controller;

import com.liangzc.demo.dto.DateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: ligang
 * @Date: 2023/8/3 10:48
 * @Description:
 */
@RestController
@RequestMapping("/date")
@Slf4j
public class DateController {

    @PostMapping("/test")
    public String TestDate(@RequestBody DateDto dateDto){
      log.info("请求参数为：{}", dateDto.getReceivableMonth());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateDto.getReceivableMonth().toInstant(), ZoneId.systemDefault());
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }
}
