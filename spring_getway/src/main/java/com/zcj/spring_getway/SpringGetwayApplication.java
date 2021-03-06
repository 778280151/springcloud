package com.zcj.spring_getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
//这个注解代替上面的eureka的客户端注解
@EnableDiscoveryClient
@SpringBootApplication
public class SpringGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGetwayApplication.class, args);
    }

}
