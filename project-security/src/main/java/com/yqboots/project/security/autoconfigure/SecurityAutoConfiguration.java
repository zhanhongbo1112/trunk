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
package com.yqboots.project.security.autoconfigure;

import com.yqboots.project.security.access.RoleHierarchyImpl;
import com.yqboots.project.security.core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;

/**
 * The Auto Configuration class for Security related beans.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
@Import({DefaultMethodSecurityConfiguration.class, AclSecurityConfiguration.class})
public class SecurityAutoConfiguration {
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public RoleHierarchy roleHierarchy() {
        return new RoleHierarchyImpl(roleRepository);
    }
}
