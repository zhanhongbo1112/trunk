package com.yqboots.project.thymeleaf.autoconfigure;

import com.yqboots.project.thymeleaf.dialect.YQBootsDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016-07-09.
 */
@Configuration
@ConditionalOnClass({YQBootsDialect.class})
public class YQBootsThymeleafAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public YQBootsDialect yqBootsDialect() {
        return new YQBootsDialect();
    }
}
