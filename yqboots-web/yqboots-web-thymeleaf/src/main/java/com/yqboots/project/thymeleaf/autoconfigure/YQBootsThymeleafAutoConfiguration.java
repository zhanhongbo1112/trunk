package com.yqboots.project.thymeleaf.autoconfigure;

import com.yqboots.project.thymeleaf.dialect.YQBootsDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

/**
 * The configuration class for the custom thymeleaf templates.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(ThymeleafProperties.class)
@ConditionalOnClass({YQBootsDialect.class})
public class YQBootsThymeleafAutoConfiguration {
    @Autowired
    private ThymeleafProperties properties;

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
}
