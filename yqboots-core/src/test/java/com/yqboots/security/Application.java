package com.yqboots.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Administrator on 2015-12-13.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {
    public static void main(String args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
