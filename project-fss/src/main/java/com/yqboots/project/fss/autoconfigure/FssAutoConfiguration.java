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
package com.yqboots.project.fss.autoconfigure;

import com.yqboots.project.fss.core.convert.StringToPathConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016-05-18.
 */
@Configuration
@EnableConfigurationProperties(FssProperties.class)
public class FssAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean({ConversionService.class})
    public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();

        bean.setConverters(additionalConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private Set<Converter> additionalConverters() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringToPathConverter());
        return converters;
    }
}
