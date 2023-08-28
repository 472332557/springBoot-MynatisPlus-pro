package com.liangzc.demo.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.Test;
import java.util.Collections;

public class NewCodeGenerator {
    @Test
    public void run() {
        String url="jdbc:mysql://106.55.227.209:3306/own?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";
        FastAutoGenerator.create(url,"root","lzc123456")
                .globalConfig(builder -> {
                    builder.author("liangzc") // 设置作者
                            .enableSwagger() //开启 swagger 模式
                            .outputDir("D://autocode");// 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.didiplus.models") // 设置父包名
                            .moduleName("sys") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml,"D://autocode/xml"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_dict_data") // 设置需要生成的表名
                            .addTablePrefix("t_","c_") ; // 设置过滤表前缀
                })
                //  .templateEngine(new FreemarkerTemplateEngine()) 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}


