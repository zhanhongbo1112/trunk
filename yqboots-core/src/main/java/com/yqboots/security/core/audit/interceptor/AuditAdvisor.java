package com.yqboots.security.core.audit.interceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015-12-14.
 */
@Component
public class AuditAdvisor extends AbstractPointcutAdvisor {
    @Autowired
    private AuditPointcut pointcut;

    @Autowired
    private AuditInterceptor advice;

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
