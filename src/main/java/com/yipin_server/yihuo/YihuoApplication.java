package com.yipin_server.yihuo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.yipin_server.yihuo.mapper","com.yipin_server.yihuo.util"})
public class YihuoApplication{

    public static void main(String[] args) {
        SpringApplication.run(YihuoApplication.class, args);
    }

}
