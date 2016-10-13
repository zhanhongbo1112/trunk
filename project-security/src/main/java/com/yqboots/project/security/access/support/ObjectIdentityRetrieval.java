package com.yqboots.project.security.access.support;

import org.springframework.security.acls.model.ObjectIdentity;

import java.io.Serializable;

/**
 * Retrieve {@link }
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public interface ObjectIdentityRetrieval {
    boolean supports(Class<?> domainObject);

    ObjectIdentity retrieve(Object domainObject);

    ObjectIdentity retrieve(Serializable id);
}
