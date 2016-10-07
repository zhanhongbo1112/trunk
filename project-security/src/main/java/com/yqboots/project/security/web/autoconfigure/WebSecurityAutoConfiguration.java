package com.yqboots.project.security.web.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The web security configuration.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/project/security/login")
                .and().rememberMe();
    }
}
