package com.liu.club_ms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.club_ms.mapper")
public class ClubMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubMsApplication.class, args);
    }

}
