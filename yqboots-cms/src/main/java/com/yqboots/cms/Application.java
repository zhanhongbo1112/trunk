package com.yqboots.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;

/**
 * Created by Administrator on 2015-12-13.
 */
@SpringBootApplication
@EntityScan(value = {"com.yqboots"})
public class Application {
    public static void main(String args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
