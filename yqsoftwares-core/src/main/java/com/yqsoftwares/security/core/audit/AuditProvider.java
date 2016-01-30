package com.yqsoftwares.security.core.audit;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016-01-30.
 */
public interface AuditProvider {
    boolean supports(Class<?> clazz);

    void beforeInvoke(Method method, Object[] arguments);

    void afterInvoke(Method method, Object[] arguments, Object returnObj);
}
