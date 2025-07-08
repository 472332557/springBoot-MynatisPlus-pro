package com.liangzc.mybatis;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

public class MybatisCodeGenerator {

    // 包路径（以表前缀为文件夹名）
    private static final String PACKAGE_PATH = "com.liangzc.demo.";

    public static void main(String[] args) {

        build("rec_receivable");
    }

    private static void build(String tableName) {
        // 表前缀
        String tablePrefix = tableName.split("_")[0];

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .outputDir(System.getProperty("user.dir") + "/src/main/java")
                .author("liangzc")
                .dateType(DateType.ONLY_DATE)
                .disableOpenDir()
//                .openDir(false)
                .build();
//        autoGenerator.setGlobalConfig(globalConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
//                .moduleName("your_module_name")
                //生成目录
                .parent(PACKAGE_PATH + tablePrefix)
                .controller("controller")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .entity("model.po")
                .build();
//        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude("rec_receivable") // 表名
                .entityBuilder()
                .naming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel)
                .columnNaming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel)
                .enableLombok()
                .build();
//        autoGenerator.setStrategy(strategyConfig);

        // 3.5.2版本需要传入配置对象
        AutoGenerator autoGenerator = new AutoGenerator(new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/bop_charge?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UT&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                "root",
                "lzc2025666"
        ).build())
                .global(globalConfig)
                .packageInfo(packageConfig)
                .strategy(strategyConfig);

        // 执行生成
        autoGenerator.execute();
    }
}