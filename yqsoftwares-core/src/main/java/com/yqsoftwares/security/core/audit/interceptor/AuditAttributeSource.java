package com.yqsoftwares.security.core.audit.interceptor;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015-12-14.
 */
public interface AuditAttributeSource {
    AuditAttribute getAuditAttribute(Method method);
}
