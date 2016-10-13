package com.yqboots.project.security.access;

import com.yqboots.project.security.Application;
import com.yqboots.project.security.access.support.ObjectIdentityRetrieval;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.io.Serializable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WithMockUser(roles = {"USER", "/SUPERVISOR"})
@Sql(scripts = {"classpath:META-INF/scripts/hsqldb/SEC_ACL_schema.sql"},
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Transactional
public class PermissionEvaluatorTest {
    @Autowired
    private MutableAclService aclService;

    @Autowired
    private PermissionEvaluator permissionEvaluator;

    @Before
    public void setUp() {
        // Prepare the information we'd like in our access control entry (ACE)
        ObjectIdentity oi = new ObjectIdentityImpl(TestMenuItem.class, "/project/menu".hashCode());
        Sid sid = new GrantedAuthoritySid("ROLE_USER");
        Permission p = BasePermission.ADMINISTRATION;
        // Create or update the relevant ACL
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oi);
        }
        // Now grant some permissions via an access control entry (ACE)
        acl.insertAce(acl.getEntries().size(), p, sid, true);

        aclService.updateAcl(acl);
    }

    @Test
    public void testHasPermission() throws Exception {
        TestMenuItem menuItem = new TestMenuItem();
        menuItem.setId(2L);
        menuItem.setUrl("/project/menu");
        menuItem.setName("PROJECT_MENU_ITEM");
        menuItem.setMenuGroup("ADMINISTRATION");
        menuItem.setMenuItemGroup("ENVIRONMENT");
        boolean result = permissionEvaluator.hasPermission(SecurityContextHolder.getContext().getAuthentication(), menuItem, "READ");
        Assert.assertTrue(!result);
    }

    @Component
    public static class TestMenuItemObjectIdentityRetrieval implements ObjectIdentityRetrieval {
        @Override
        public boolean supports(final Class<?> domainObject) {
            return domainObject.isAssignableFrom(TestMenuItem.class);
        }

        @Override
        public ObjectIdentity retrieve(final Object domainObject) {
            TestMenuItem testMenuItem = (TestMenuItem) domainObject;
            return new ObjectIdentityImpl(TestMenuItem.class, testMenuItem.getUrl().hashCode());
        }

        @Override
        public ObjectIdentity retrieve(final Serializable id) {
            return new ObjectIdentityImpl(TestMenuItem.class, id.hashCode());
        }
    }
}
