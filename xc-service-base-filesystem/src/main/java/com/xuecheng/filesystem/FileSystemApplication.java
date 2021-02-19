package com.xuecheng.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//扫描所在包及子包的bean，注入到ioc中
@EntityScan("com.xuecheng.framework.domain.filesystem")//扫描实体类
//扫描接口,framework中通用类,本项目下的所有类
@ComponentScan(basePackages = {"com.xuecheng.api", "com.xuecheng.framework", "com.xuecheng.filesystem"})
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }
}
