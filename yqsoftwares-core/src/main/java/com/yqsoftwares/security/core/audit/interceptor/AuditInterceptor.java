package com.yqsoftwares.security.core.audit.interceptor;

import com.yqsoftwares.security.core.audit.AuditProvider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-12-14.
 */
@Component
public class AuditInterceptor implements MethodInterceptor, Serializable {
    private List<AuditProvider> auditProviders = new ArrayList<>();

    @Autowired
    public AuditInterceptor(List<AuditProvider> auditProviders) {
        super();
        this.auditProviders = auditProviders;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnObj = null;

        boolean found = false;

        for (AuditProvider auditProvider : auditProviders) {
            if (auditProvider.supports(invocation.getMethod().getDeclaringClass())) {
                found = true;

                final Object[] arguments = invocation.getArguments();

                auditProvider.beforeInvocation(invocation.getMethod(), arguments);

                returnObj = invocation.proceed();

                auditProvider.afterInvocation(invocation.getMethod(), arguments, returnObj);

                break;
            }
        }

        if (!found) {
            returnObj = invocation.proceed();
        }

        return returnObj;
    }
}
