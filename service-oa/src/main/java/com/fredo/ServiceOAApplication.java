package com.fredo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.fredo") 默认扫描当前包及其子包
//@MapperScan("com.fredo.*.mapper") 放到了 MybatisPlusConfig 中
public class ServiceOAApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOAApplication.class, args);
    }

}