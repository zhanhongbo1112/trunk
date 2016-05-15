package com.yqboots.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.TimeZone;

/**
 * Created by Administrator on 2016-05-01.
 */
@Configuration
public class WebApplication extends WebMvcConfigurerAdapter {
    @Autowired
    private WebMvcProperties mvcProperties = new WebMvcProperties();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver bean = new SessionLocaleResolver();
        bean.setDefaultLocale(mvcProperties.getLocale());
        bean.setDefaultTimeZone(TimeZone.getDefault());

        return bean;
    }
}
