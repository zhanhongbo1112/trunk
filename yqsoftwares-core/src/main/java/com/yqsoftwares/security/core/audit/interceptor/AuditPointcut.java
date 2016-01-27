package com.yqsoftwares.security.core.audit.interceptor;

import com.yqsoftwares.security.core.audit.annotation.Auditable;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015-12-14.
 */
@Component
public class AuditPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(Auditable.class);
    }
}
