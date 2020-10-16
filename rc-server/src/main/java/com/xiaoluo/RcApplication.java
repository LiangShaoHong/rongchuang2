package com.xiaoluo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.layui.mapper")
public class RcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcApplication.class, args);
    }


}

