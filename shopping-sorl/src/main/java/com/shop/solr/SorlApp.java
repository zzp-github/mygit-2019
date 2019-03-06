package com.shop.solr;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shop.mapper")
public class SorlApp {
    public static void main(String[] args) {
        SpringApplication.run(SorlApp.class, args);
    }

}

