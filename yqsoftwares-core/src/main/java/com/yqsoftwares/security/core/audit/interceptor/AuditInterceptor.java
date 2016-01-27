package com.yqsoftwares.security.core.audit.interceptor;

import com.yqsoftwares.security.core.audit.Audit;
import com.yqsoftwares.security.core.audit.repository.AuditRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-12-14.
 */
@Component
public class AuditInterceptor implements MethodInterceptor, Serializable {
    private AuditRepository auditRepository;

    private AuditAttributeSource auditAttributeSource;

    @Autowired
    public AuditInterceptor(AuditRepository auditRepository, AuditAttributeSource auditAttributeSource) {
        this();
        this.auditRepository = auditRepository;
        this.auditAttributeSource = auditAttributeSource;
    }

    protected AuditInterceptor() {
        super();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final AuditAttribute attr = auditAttributeSource.getAuditAttribute(invocation.getMethod());

        final Object returnObj = invocation.proceed();

//        final Object[] arguments = invocation.getArguments();

        Audit entity = new Audit(attr.getCode());

        auditRepository.save(entity);

        return returnObj;
    }
}
