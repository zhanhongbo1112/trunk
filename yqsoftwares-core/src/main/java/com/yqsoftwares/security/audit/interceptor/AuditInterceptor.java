package com.yqsoftwares.security.audit.interceptor;

import com.yqsoftwares.security.audit.Audit;
import com.yqsoftwares.security.audit.repository.AuditRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditInterceptor implements MethodInterceptor, Serializable {
    private AuditRepository auditRepository;

    private AuditAttributeSource auditAttributeSource;

    public AuditInterceptor() {
        super();
    }

    public AuditInterceptor(AuditRepository auditRepository, AuditAttributeSource auditAttributeSource) {
        this.auditRepository = auditRepository;
        this.auditAttributeSource = auditAttributeSource;
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

    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void setAuditAttributeSource(AuditAttributeSource auditAttributeSource) {
        this.auditAttributeSource = auditAttributeSource;
    }
}
