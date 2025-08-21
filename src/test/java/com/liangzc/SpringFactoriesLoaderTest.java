package com.liangzc;

import com.liangzc.demo.DemoApplication;
import com.liangzc.demo.transaction.service.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.SpringFactoriesLoader;
import java.lang.reflect.Method;
import java.util.List;

/**
 * SpringFactoriesLoader测试
 * SpringFactoriesLoader 是 Spring 框架提供的一个工厂加载机制，用于从类路径下的 META-INF/spring.factories 文件中加载配置的类（通常是接口 / 抽象类的实现类），
 * 核心作用是支持 SPI（Service Provider Interface）模式，实现框架的可扩展插件化设计。
 *
 * 一、SpringFactoriesLoader 核心原理
 *  1、配置文件：在类路径的 META-INF 目录下创建 spring.factories 文件，格式为 接口全限定名=实现类全限定名（多个实现类用逗号分隔）。
 *  2、加载逻辑：通过 SpringFactoriesLoader 的静态方法（如 loadFactories 或 loadFactoryNames）读取配置文件，加载并实例化指定接口的所有实现类。
 *  3、作用：无需硬编码，通过配置文件即可动态扩展接口的实现，实现 “接口与实现分离”，方便框架扩展（如第三方组件集成）。
 */
@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class SpringFactoriesLoaderTest {

    @Test
    public void getNames(){
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(MessageSender.class, getClass().getClassLoader());
        for (String string : strings) {
            log.info("{}",string);
            try {
                Class<?> aClass = Class.forName(string);
                log.info("{}",aClass.newInstance());
                Object newInstance = aClass.newInstance();
                Method method = aClass.getMethod("sendMessage", String.class);
                method.invoke(newInstance,"hello world");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getFactory(){
        List<MessageSender> messageSenders = SpringFactoriesLoader.loadFactories(MessageSender.class, null);
        messageSenders.forEach(messageSender -> {
            messageSender.sendMessage("hello world");
        });
    }
}
