package com.yqboots.security.access.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2016-05-03.
 */
@Configuration
public class AclConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public LoggerListener loggerListener() {
        return new LoggerListener();
    }

    @Bean
    public AclService jdbcMutableAclService() {
        return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    }

    @Bean
    public PermissionEvaluator aclPermissionEvaluator() {
        return new AclPermissionEvaluator(jdbcMutableAclService());
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        AclAuthorizationStrategyImpl aclAuthorizationStrategy = new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));

        return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy, new ConsoleAuditLogger());
    }

    @Bean
    public AclCache aclCache() {
        return new SpringCacheBasedAclCache(aclNoOpCache(),
                new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger()),
                new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN")));
    }

    @Bean
    public Cache aclNoOpCache() {
        // TODO: change implementation
        return aclNoOpCacheManager().getCache("aclCache");
    }

    @Bean
    public CacheManager aclNoOpCacheManager() {
        // TODO: change implementation
        return new NoOpCacheManager();
    }
}
