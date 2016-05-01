package com.yqboots.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Created by Administrator on 2016-05-01.
 */
@Configuration
public class WebApplication extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // @Bean
    public LocaleResolver localeResolver() {
        /* TODO: use SessionLocaleResolver
        SessionLocaleResolver.LOCALE=en
        SessionLocaleResolver.TIME_ZONE=GMT+08:00
         */
        return new SessionLocaleResolver();
    }

    private LocaleChangeInterceptor localeChangeInterceptor() {
        // TODO: change "locale" request parameter
        return new LocaleChangeInterceptor();
    }
}
