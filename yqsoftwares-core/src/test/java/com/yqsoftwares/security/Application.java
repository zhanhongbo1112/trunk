package com.yqsoftwares.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Administrator on 2015-12-13.
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application {
    public static void main(String args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
