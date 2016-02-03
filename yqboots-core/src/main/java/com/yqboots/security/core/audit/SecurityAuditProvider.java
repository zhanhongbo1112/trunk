package com.yqboots.security.core.audit;

import com.yqboots.security.core.*;
import com.yqboots.security.core.audit.interceptor.AuditAttribute;
import com.yqboots.security.core.audit.interceptor.AuditAttributeSource;
import com.yqboots.security.core.audit.repository.AuditRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016-01-30.
 */
@Service
public class SecurityAuditProvider extends AuditProviderSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditProviderSupport.class);

    private final AuditProvider[] delegates = new AuditProvider[]{new UserAuditProvider(), new GroupAuditProvider(), new RoleAuditProvider()};

    @Autowired
    private AuditAttributeSource auditAttributeSource;

    @Autowired
    private AuditRepository auditRepository;

    private static StringBuilder append(String label, Object... values) {
        StringBuilder sb = new StringBuilder(64);
        if (ArrayUtils.isNotEmpty(values)) {
            sb.append(label).append(": ");
            for (Object value : values) {
                sb.append(value).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        for (AuditProvider delegate : delegates) {
            if (delegate.supports(clazz)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void afterInvocation(Method method, Object[] arguments, Object returnObj) {
        for (AuditProvider delegate : delegates) {
            if (delegate.supports(method.getDeclaringClass())) {
                delegate.afterInvocation(method, arguments, returnObj);
            }
        }
    }

    public void setAuditAttributeSource(AuditAttributeSource auditAttributeSource) {
        this.auditAttributeSource = auditAttributeSource;
    }

    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    private class UserAuditProvider extends AuditProviderSupport {
        @Override
        public boolean supports(Class<?> clazz) {
            return UserManager.class.isAssignableFrom(clazz);
        }

        @Override
        public void afterInvocation(Method method, Object[] arguments, Object returnObj) {
            final AuditAttribute attr = auditAttributeSource.getAuditAttribute(method);

            SecurityAudit audit = new SecurityAudit(attr.getCode());

            ToStringBuilder sb = new ToStringBuilder(null, ToStringStyle.MULTI_LINE_STYLE);
            User user;
            switch (attr.getCode()) {
                case SecurityAudit.CODE_ADD_USER:
                case SecurityAudit.CODE_UPDATE_USER:
                    user = (User) arguments[0];
                    audit.setTarget(user.getUsername());
                    sb.append("enabled", user.isEnabled());
                    break;
                case SecurityAudit.CODE_ADD_GROUPS_TO_USER:
                case SecurityAudit.CODE_UPDATE_GROUPS_OF_USER:
                case SecurityAudit.CODE_REMOVE_GROUPS_FROM_USER:
                    audit.setTarget(arguments[0].toString());
                    sb.append("groups", arguments[1]);
                    break;
                case SecurityAudit.CODE_ADD_ROLES_TO_USER:
                case SecurityAudit.CODE_UPDATE_ROLES_OF_USER:
                case SecurityAudit.CODE_REMOVE_ROLES_FROM_USER:
                    audit.setTarget(arguments[0].toString());
                    sb.append("roles", arguments[1]);
                    break;
                case SecurityAudit.CODE_REMOVE_USER:
                    audit.setTarget(arguments[0].toString());
                    break;
                default:
                    LOGGER.error("Invalid code for UserManager operations: " + attr.getCode());
                    break;
            }
            audit.setDescription(StringUtils.remove(sb.toString(), "<null>"));

            auditRepository.save(audit);
        }
    }

    private class GroupAuditProvider extends AuditProviderSupport {
        @Override
        public boolean supports(Class<?> clazz) {
            return GroupManager.class.isAssignableFrom(clazz);
        }

        @Override
        public void afterInvocation(Method method, Object[] arguments, Object returnObj) {
            final AuditAttribute attr = auditAttributeSource.getAuditAttribute(method);

            SecurityAudit audit = new SecurityAudit(attr.getCode());

            ToStringBuilder sb = new ToStringBuilder(null, ToStringStyle.MULTI_LINE_STYLE);
            Group group;
            switch (attr.getCode()) {
                case SecurityAudit.CODE_ADD_GROUP:
                case SecurityAudit.CODE_UPDATE_GROUP:
                    group = (Group) arguments[0];
                    audit.setTarget(group.getPath());
                    sb.append("alias", group.getAlias()).append("description", group.getDescription());
                    break;
                case SecurityAudit.CODE_ADD_USERS_TO_GROUP:
                case SecurityAudit.CODE_UPDATE_USERS_OF_GROUP:
                case SecurityAudit.CODE_REMOVE_USERS_FROM_GROUP:
                    audit.setTarget(arguments[0].toString());
                    sb.append("users", arguments[1]);
                    break;
                case SecurityAudit.CODE_ADD_ROLES_TO_GROUP:
                case SecurityAudit.CODE_UPDATE_ROLES_OF_GROUP:
                case SecurityAudit.CODE_REMOVE_ROLES_FROM_GROUP:
                    audit.setTarget(arguments[0].toString());
                    sb.append("roles", arguments[1]);
                    break;
                case SecurityAudit.CODE_REMOVE_GROUP:
                    audit.setTarget(arguments[0].toString());
                    break;
                default:
                    LOGGER.error("Invalid code for GroupManager operations: " + attr.getCode());
                    break;
            }
            audit.setDescription(StringUtils.remove(sb.toString(), "<null>"));

            auditRepository.save(audit);
        }
    }

    private class RoleAuditProvider extends AuditProviderSupport {
        @Override
        public boolean supports(Class<?> clazz) {
            return RoleManager.class.isAssignableFrom(clazz);
        }

        @Override
        public void afterInvocation(Method method, Object[] arguments, Object returnObj) {
            final AuditAttribute attr = auditAttributeSource.getAuditAttribute(method);

            SecurityAudit audit = new SecurityAudit(attr.getCode());

            ToStringBuilder sb = new ToStringBuilder(null, ToStringStyle.MULTI_LINE_STYLE);
            Role role;
            switch (attr.getCode()) {
                case SecurityAudit.CODE_ADD_ROLE:
                case SecurityAudit.CODE_UPDATE_ROLE:
                    role = (Role) arguments[0];
                    audit.setTarget(role.getPath());
                    sb.append("alias", role.getAlias()).append("description", role.getDescription());
                    break;
                case SecurityAudit.CODE_ADD_USERS_TO_ROLE:
                case SecurityAudit.CODE_UPDATE_USERS_OF_ROLE:
                case SecurityAudit.CODE_REMOVE_USERS_FROM_ROLE:
                    audit.setTarget(arguments[0].toString());
                    sb.append("users", arguments[1]);
                    break;
                case SecurityAudit.CODE_ADD_GROUPS_TO_ROLE:
                case SecurityAudit.CODE_UPDATE_GROUPS_OF_ROLE:
                case SecurityAudit.CODE_REMOVE_GROUPS_FROM_ROLE:
                    audit.setTarget(arguments[0].toString());
                    sb.append("groups", arguments[1]);
                    break;
                case SecurityAudit.CODE_REMOVE_ROLE:
                    audit.setTarget(arguments[0].toString());
                    break;
                default:
                    LOGGER.error("Invalid code for RoleManager operations: " + attr.getCode());
                    break;
            }
            audit.setDescription(StringUtils.remove(sb.toString(), "<null>"));

            auditRepository.save(audit);
        }
    }
}
