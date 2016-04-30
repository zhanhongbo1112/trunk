package com.yqboots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;

/**
 * Created by Administrator on 2016-04-29.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public IMessageResolver thymeleafMessageResolver() {
        return new SpringMessageResolver();
    }
}
