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
package com.yqboots.security.web.autoconfigure;

import com.yqboots.security.web.support.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The web security configuration.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Import({AllRolesDataDictResolver.class, UserRolesDataDictResolver.class, GroupRolesDataDictResolver.class,
        AllGroupsDataDictResolver.class, UserGroupsDataDictResolver.class, RoleGroupsDataDictResolver.class,
        AllUsersDataDictResolver.class, GroupUsersDataDictResolver.class, RoleUsersDataDictResolver.class
})
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().csrf().disable().formLogin()
                .loginPage("/security/login").loginProcessingUrl("/login").and().rememberMe().and().exceptionHandling()
                .accessDeniedPage("/security/403");
    }
}
