package com.yqboots.project.security.access;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Database-based {@link PermissionEvaluator} to determine whether a user has a permission or
 * permissions for a given domain object.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class AclPermissionEvaluatorImpl implements PermissionEvaluator {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication, final Object targetDomainObject, final Object permission) {
        // TODO: hasPermission
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final Authentication authentication, final Serializable targetId, final String targetType, final Object permission) {
        // TODO: hasPermission
        return true;
    }
}
