package com.syxu.yaofang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.syxu.yaofang.mapper")
@EnableScheduling
public class YaofangApplication {

    public static void main(String[] args) {
        SpringApplication.run(YaofangApplication.class, args);
    }

}
