package com.yqboots.security.core.audit.annotation;

import com.yqboots.security.core.audit.interceptor.AuditAttribute;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by Administrator on 2015-12-14.
 */
public interface AuditAnnotationParser {
    AuditAttribute parseAuditAnnotation(AnnotatedElement ae);
}
