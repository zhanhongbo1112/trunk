package com.yqboots.security.access.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by Administrator on 2016-05-02.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DefaultMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private PermissionEvaluator aclPermissionEvaluator;

    @Autowired
    private AclService aclService;

    /**
     * Provide a {@link MethodSecurityExpressionHandler} that is registered with the
     * {@link ExpressionBasedPreInvocationAdvice}. The default is
     * {@link DefaultMethodSecurityExpressionHandler} which optionally will Autowire an
     * {@link AuthenticationTrustResolver}.
     * <p>
     * <p>
     * Subclasses may override this method to provide a custom
     * {@link MethodSecurityExpressionHandler}
     * </p>
     *
     * @return
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler bean = new DefaultMethodSecurityExpressionHandler();
        bean.setPermissionEvaluator(aclPermissionEvaluator);
        bean.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService));

        return bean;
    }


}
