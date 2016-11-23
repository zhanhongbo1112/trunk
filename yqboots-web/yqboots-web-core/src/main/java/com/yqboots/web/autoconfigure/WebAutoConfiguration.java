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
package com.yqboots.web.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.TimeZone;

/**
 * The configuration class for web module.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication
public class WebAutoConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private WebMvcProperties mvcProperties = new WebMvcProperties();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver bean = new SessionLocaleResolver();
        bean.setDefaultLocale(mvcProperties.getLocale());
        bean.setDefaultTimeZone(TimeZone.getDefault());

        return bean;
    }

    @Bean
    public FilterRegistrationBean errorPageFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        ErrorPageFilter errorPageFilter = new ErrorPageFilter();
        errorPageFilter.addErrorPages(new ErrorPage(MultipartException.class, "/error"));
        registration.setFilter(errorPageFilter);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }
}
