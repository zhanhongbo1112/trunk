package com.yqsoftwares.security.audit.annotation;

import com.yqsoftwares.security.audit.interceptor.AuditAttribute;
import com.yqsoftwares.security.audit.interceptor.AuditAttributeSource;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AnnotationAuditAttributeSource implements AuditAttributeSource, Serializable {
    private final AuditAnnotationParser auditAnnotationParser;

    public AnnotationAuditAttributeSource(AuditAnnotationParser auditAnnotationParser) {
        super();
        this.auditAnnotationParser = auditAnnotationParser;
    }

    @Override
    public AuditAttribute getAuditAttribute(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        return this.auditAnnotationParser.parseAuditAnnotation(method);
    }

    public AuditAnnotationParser getAuditAnnotationParser() {
        return this.auditAnnotationParser;
    }
}
