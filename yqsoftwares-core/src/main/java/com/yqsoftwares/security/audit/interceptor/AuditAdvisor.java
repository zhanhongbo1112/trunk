package com.yqsoftwares.security.audit.interceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditAdvisor extends AbstractPointcutAdvisor {
    private AuditPointcut pointcut;
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
