package com.yqboots.security.core.audit;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016-01-30.
 */
public interface AuditProvider {
    boolean supports(Class<?> clazz);

    void beforeInvocation(Method method, Object[] arguments);

    void afterInvocation(Method method, Object[] arguments, Object returnObj);
}
