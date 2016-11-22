package com.yqboots.security.access;

import com.yqboots.security.Application;
import com.yqboots.security.access.support.ObjectIdentityRetrieval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WithMockUser(roles = {"USER", "/USER"})
@Sql(scripts = {"classpath:META-INF/scripts/hsqldb/SEC_ACL_schema.sql", "PermissionEvaluatorTest.sql"},
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class PermissionEvaluatorTest {
    @Autowired
    private AclService aclService;

    @Autowired
    private PermissionEvaluator permissionEvaluator;

    @Test
    public void testHasPermission() throws Exception {
        TestMenuItem menuItem = new TestMenuItem();
        menuItem.setId(2L);
        menuItem.setUrl("/menu");
        menuItem.setName("PROJECT_MENU_ITEM");
        menuItem.setMenuGroup("ADMINISTRATION");
        menuItem.setMenuItemGroup("ENVIRONMENT");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean result = permissionEvaluator.hasPermission(auth, menuItem, "READ");
        Assert.assertTrue(result);
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
            return new ObjectIdentityImpl(TestMenuItem.class, (long) testMenuItem.getUrl().hashCode());
        }

        @Override
        public ObjectIdentity retrieve(final Serializable id) {
            return new ObjectIdentityImpl(TestMenuItem.class, id.hashCode());
        }
    }
}
