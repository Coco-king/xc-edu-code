package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient //注册到eureka注册中心
@EntityScan({"com.xuecheng.framework.domain.cms"}) //扫描实体类的注解
//扫描接口微服务的注解，世纪大坑：如果使用swagger，那么一定要确保启动类可以扫描到swagger配置类的EnableSwagger2注解
@ComponentScan({"com.xuecheng.api", "com.xuecheng.framework", "com.xuecheng.manage_cms"})
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
