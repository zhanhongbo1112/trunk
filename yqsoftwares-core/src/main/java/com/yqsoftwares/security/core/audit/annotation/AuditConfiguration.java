package com.yqsoftwares.security.core.audit.annotation;

import com.yqsoftwares.security.core.audit.AuditProvider;
import com.yqsoftwares.security.core.audit.SecurityAuditProvider;
import com.yqsoftwares.security.core.audit.interceptor.AuditAttributeSource;
import com.yqsoftwares.security.core.audit.interceptor.AuditInterceptor;
import com.yqsoftwares.security.core.audit.interceptor.AuditPointcut;
import com.yqsoftwares.security.core.audit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-30.
 */
@Configuration
public class AuditConfiguration {
    @Autowired
    private AuditRepository auditRepository;

    @Bean
    public AuditPointcut auditPointcut() {
        return new AuditPointcut();
    }

    @Bean
    public AuditInterceptor auditInterceptor() {
        List<AuditProvider> providers = new ArrayList<>();
        providers.add(new SecurityAuditProvider());
        return new AuditInterceptor(providers);
    }

    @Bean
    public AuditAttributeSource auditAttributeSource() {
        return new AnnotationAuditAttributeSource(new AuditAnnotationParserImpl());
    }
}
