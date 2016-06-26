package com.yqboots.project.modeler.autoconfigure;

import com.yqboots.project.modeler.core.ProjectModeler;
import com.yqboots.project.modeler.core.ProjectModelerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016-06-09.
 */
@Configuration
@EnableConfigurationProperties(ProjectModelerProperties.class)
public class ProjectModelerAutoConfiguration {
    @Autowired
    private ProjectModelerProperties properties;

    @Bean
    public ProjectModeler projectModeler() throws Exception {
        return new ProjectModelerImpl();
    }
}
