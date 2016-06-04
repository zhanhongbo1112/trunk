/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.prototype.project.autoconfigure;

import com.yqboots.prototype.project.core.ProjectInitializer;
import com.yqboots.prototype.project.core.MavenProjectInitializer;
import com.yqboots.prototype.project.velocity.CustomVelocityEngineFactoryBean;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.Properties;

/**
 * Created by Administrator on 2016-05-28.
 */
@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
public class ProjectAutoConfiguration {
    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    protected VelocityProperties velocityProperties;

    @Bean
    public ProjectInitializer mavenProjectInitializer() {
        return new MavenProjectInitializer(this.velocityEngine, this.velocityProperties);
    }

    @Configuration
    public static class VelocityConfiguration {
        @Autowired
        private ProjectProperties projectProperties;

        @Autowired
        protected VelocityProperties velocityProperties;

        @Bean
        public VelocityEngineFactoryBean velocityConfiguration() throws Exception {
            CustomVelocityEngineFactoryBean velocityEngineFactoryBean =
                    new CustomVelocityEngineFactoryBean(velocityProperties);
            velocityEngineFactoryBean.setRoot(projectProperties.getSourcePath());
            applyProperties(velocityEngineFactoryBean);
            velocityEngineFactoryBean.afterPropertiesSet();
            return velocityEngineFactoryBean;
        }

        private void applyProperties(VelocityEngineFactory factory) {
            factory.setResourceLoaderPath(this.velocityProperties.getResourceLoaderPath());
            factory.setPreferFileSystemAccess(this.velocityProperties.isPreferFileSystemAccess());
            Properties velocityProperties = new Properties();
            velocityProperties.setProperty("input.encoding", this.velocityProperties.getCharsetName());
            velocityProperties.putAll(this.velocityProperties.getProperties());
            factory.setVelocityProperties(velocityProperties);
        }
    }
}
