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
import com.yqboots.prototype.project.core.ProjectInitializerImpl;
import com.yqboots.prototype.project.core.velocity.CustomVelocityEngine;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by Administrator on 2016-05-28.
 */
@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
public class ProjectAutoConfiguration {
    @Autowired
    private ProjectProperties projectProperties;

    @Bean
    public ProjectInitializer projectInitializer() throws Exception {
        return new ProjectInitializerImpl(starterVelocityEngine());
    }

    protected void applyProperties(final VelocityEngineFactory factory, final String resourceLoaderPath) {
        factory.setResourceLoaderPath(resourceLoaderPath);
        factory.setPreferFileSystemAccess(false);

        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", Charset.forName("UTF-8").name());
        velocityProperties.putAll(this.projectProperties.getProperties());
        factory.setVelocityProperties(velocityProperties);
    }

    private VelocityEngine starterVelocityEngine() throws Exception {
        VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean() {
            @Override
            protected VelocityEngine newVelocityEngine() throws IOException, VelocityException {
                // TODO: custom velocity engine to include FileBuilders
                return new CustomVelocityEngine();
            }
        };
        applyProperties(velocityEngineFactoryBean, "classpath:/vm/starter/");
        velocityEngineFactoryBean.afterPropertiesSet();
        return velocityEngineFactoryBean.getObject();
    }
}
