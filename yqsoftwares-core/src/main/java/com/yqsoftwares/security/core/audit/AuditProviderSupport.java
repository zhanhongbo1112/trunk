package com.yqsoftwares.security.core.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016-01-31.
 */
public abstract class AuditProviderSupport implements AuditProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditProviderSupport.class);

    @Override
    public void beforeInvocation(Method method, Object[] arguments) {
        LOGGER.debug(method.getName());
    }

    @Override
    public void afterInvocation(Method method, Object[] arguments, Object returnObj) {
        LOGGER.debug(method.getName());
    }
}
