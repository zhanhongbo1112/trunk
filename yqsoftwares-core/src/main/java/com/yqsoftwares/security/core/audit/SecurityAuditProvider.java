package com.yqsoftwares.security.core.audit;

import com.yqsoftwares.security.core.GroupManager;
import com.yqsoftwares.security.core.RoleManager;
import com.yqsoftwares.security.core.UserManager;
import com.yqsoftwares.security.core.audit.interceptor.AuditAttribute;
import com.yqsoftwares.security.core.audit.interceptor.AuditAttributeSource;
import com.yqsoftwares.security.core.audit.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016-01-30.
 */
@Service
public class SecurityAuditProvider implements AuditProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAuditProvider.class);

    @Autowired
    private AuditAttributeSource auditAttributeSource;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserManager.class.isAssignableFrom(clazz)
                || GroupManager.class.isAssignableFrom(clazz)
                || RoleManager.class.isAssignableFrom(clazz);
    }

    @Override
    public void beforeInvoke(Method method, Object[] arguments) {
        LOGGER.debug(method.getName());
    }

    @Override
    public void afterInvoke(Method method, Object[] arguments, Object returnObj) {
        final AuditAttribute attr = auditAttributeSource.getAuditAttribute(method);

        auditRepository.save(new SecurityAudit(attr.getCode()));
    }

    public void setAuditAttributeSource(AuditAttributeSource auditAttributeSource) {
        this.auditAttributeSource = auditAttributeSource;
    }

    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
}
