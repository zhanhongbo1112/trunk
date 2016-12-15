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
package com.yqboots.web.thymeleaf.autoconfigure;

import com.yqboots.web.thymeleaf.dialect.YQBootsDialect;
import com.yqboots.web.thymeleaf.support.Html2PdfGenerator;
import com.yqboots.web.thymeleaf.support.HtmlOptionsResolver;
import com.yqboots.web.thymeleaf.support.HtmlElementResolvers;
import com.yqboots.web.thymeleaf.support.HtmlTreeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration class for the custom thymeleaf templates.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter(value = {org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class})
@EnableConfigurationProperties({ThymeleafProperties.class, ThymeleafExtensionProperties.class})
@ConditionalOnClass({YQBootsDialect.class})
public class ThymeleafAutoConfiguration {
    @Autowired
    private ThymeleafProperties properties;

    @Autowired
    private ThymeleafExtensionProperties customProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired(required = false)
    private List<HtmlOptionsResolver> htmlOptionsResolvers = new ArrayList<>();

    @Autowired(required = false)
    private List<HtmlTreeResolver> htmlTreeResolvers = new ArrayList<>();

    /**
     * Defines the custom Dialect for the framework.
     *
     * @return the custom Thymeleaf Dialect
     */
    @Bean
    @ConditionalOnMissingBean
    public YQBootsDialect yqBootsDialect() {
        return new YQBootsDialect();
    }

    /**
     * The extra TemplateResolver for resource loading.
     *
     * @return SpringResourceTemplateResolver
     */
    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix(this.properties.getPrefix());
        resolver.setSuffix(this.properties.getSuffix());
        resolver.setTemplateMode(this.properties.getMode());
        if (this.properties.getEncoding() != null) {
            resolver.setCharacterEncoding(this.properties.getEncoding().name());
        }
        resolver.setCacheable(this.properties.isCache());
        Integer order = this.properties.getTemplateResolverOrder();
        if (order != null) {
            resolver.setOrder(order + 1);
        }

        return resolver;
    }

    @Bean
    public HtmlElementResolvers htmlElementResolvers() {
        HtmlElementResolvers bean = new HtmlElementResolvers();
        bean.setHtmlOptionsResolvers(htmlOptionsResolvers);
        bean.setHtmlTreeResolvers(htmlTreeResolvers);
        return bean;
    }

    @Bean
    @ConditionalOnClass({ITextRenderer.class})
    public Html2PdfGenerator html2PdfGenerator() {
        return new Html2PdfGenerator(templateEngine, customProperties.getFonts());
    }
}
