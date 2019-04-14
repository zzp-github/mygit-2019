package com.shop.seller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.shop.mapper")
public class SellerApp {
    public static void main(String[] args){
        SpringApplication.run(SellerApp.class, args);
    }
}
