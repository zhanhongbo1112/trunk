package com.yqboots.project.security.autoconfigure;

import com.yqboots.project.security.access.support.DelegatingObjectIdentityRetrievalStrategy;
import com.yqboots.project.security.access.support.ObjectIdentityRetrieval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.List;

/**
 * Configuration for ACL.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class AclSecurityConfiguration {
    public static final String ACL_CACHE_NAME = "acls";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired(required = false)
    private List<ObjectIdentityRetrieval> objectIdentityRetrievals;

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        AclPermissionEvaluator bean = new AclPermissionEvaluator(aclService());
        bean.setObjectIdentityRetrievalStrategy(aclObjectIdentityRetrievalStrategy());
        bean.setSidRetrievalStrategy(aclSidRetrievalStrategy());
        return bean;
    }

    @Bean
    public AclService aclService() {
        return new JdbcMutableAclService(dataSource, aclLookupStrategy(), aclCache());
    }

    @Bean
    public LookupStrategy aclLookupStrategy() {
        return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(),
                aclPermissionGrantingStrategy());
    }

    @Bean
    public AclCache aclCache() {
        return new SpringCacheBasedAclCache(new ConcurrentMapCache(ACL_CACHE_NAME), aclPermissionGrantingStrategy(),
                aclAuthorizationStrategy());
    }

    @Bean
    public PermissionGrantingStrategy aclPermissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("SUPERVISOR"));
    }

    @Bean
    public ObjectIdentityRetrievalStrategy aclObjectIdentityRetrievalStrategy() {
        return new DelegatingObjectIdentityRetrievalStrategy(objectIdentityRetrievals);
    }

    @Bean
    public SidRetrievalStrategy aclSidRetrievalStrategy() {
        return new SidRetrievalStrategyImpl(roleHierarchy);
    }
}
