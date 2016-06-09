package com.yqboots.prototype.model.autoconfigure;

import com.yqboots.prototype.model.core.ProjectModeler;
import com.yqboots.prototype.model.core.ProjectModelerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016-06-09.
 */
@Configuration
@EnableConfigurationProperties(ModelProperties.class)
public class ModelAutoConfiguration {
    @Autowired
    private ModelProperties properties;

    @Bean
    public ProjectModeler projectModeler() throws Exception {
        return new ProjectModelerImpl();
    }
}
