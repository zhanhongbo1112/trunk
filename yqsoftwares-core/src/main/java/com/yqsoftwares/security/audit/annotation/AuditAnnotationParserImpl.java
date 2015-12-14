package com.yqsoftwares.security.audit.annotation;

import com.yqsoftwares.security.audit.interceptor.AuditAttribute;
import com.yqsoftwares.security.audit.interceptor.AuditAttributeImpl;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditAnnotationParserImpl implements AuditAnnotationParser {
    @Override
    public AuditAttribute parseAuditAnnotation(AnnotatedElement ae) {
        AuditAttribute result = null;

        final Auditable ann = AnnotationUtils.getAnnotation(ae, Auditable.class);
        if (ann != null) {
            result = parseAuditAnnotation(ann);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    private AuditAttribute parseAuditAnnotation(Auditable ann) {
        return new AuditAttributeImpl(ann.code());
    }
}
