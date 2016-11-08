package com.yqboots.security.core;

import com.yqboots.security.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016-01-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Sql(scripts = "00_init.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(scripts = "01_destroy.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@WithUserDetails(value="admin")
public class RoleManagerTest {
    @Autowired
    private RoleManager roleManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test(expected = RoleExistsException.class)
    public void testAddRole() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_ROLE", "PATH='/USERS/AGENTS'");
        assertTrue(count == 0);

        Role entity = new Role();
        entity.setPath("/USERS/AGENTS");
        entity.setAlias("AGENTS");
        entity.setDescription("Agent Role");

        roleManager.addRole(entity);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_ROLE", "PATH='/USERS/AGENTS'");
        assertTrue(count == 1);

        entity = new Role();
        entity.setPath("/USERS/AGENTS");
        entity.setAlias("AGENTS");
        entity.setDescription("Agent Role");

        roleManager.addRole(entity);
    }

    @Test
    public void testUpdateRole() throws Exception {
        Role role = roleManager.findRole("/ADMINS");
        assertEquals(role.getAlias(), "ADMINS");
        assertEquals(role.getDescription(), "ADMINS");

        role.setAlias("ADMINS_MODIFIED");
        role.setDescription("ADMINS_D_MODIFIED");
        roleManager.updateRole(role);

        role = roleManager.findRole("/ADMINS");
        assertEquals(role.getAlias(), "ADMINS_MODIFIED");
        assertEquals(role.getDescription(), "ADMINS_D_MODIFIED");
    }

    @Test
    public void testUpdateUsers() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 and ROLE_ID=1");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 and ROLE_ID=1");
        assertTrue(count == 0);

        String[] inUsers = new String[]{"user"}; // USER_ID = 2
        roleManager.updateUsers("/ADMINS", inUsers);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 and ROLE_ID=1");
        assertTrue(count == 0);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 and ROLE_ID=1");
        assertTrue(count == 1);
    }

    @Test
    public void testUpdateGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=2 and ROLE_ID=1");
        assertTrue(count == 0);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 and ROLE_ID=1");
        assertTrue(count == 1);

        String[] inGroups = new String[]{"/USERS"}; // GROUP_ID = 2
        roleManager.updateGroups("/ADMINS", inGroups);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=2 and ROLE_ID=1");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 and ROLE_ID=1");
        assertTrue(count == 0);
    }

    @Test
    public void testRemoveRole() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_ROLE", "PATH='/USERS'");
        assertTrue(count == 1);

        roleManager.removeRole("/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_ROLE", "PATH='/USERS'");
        assertTrue(count == 0);
    }

    @Test
    public void testRemoveUsers() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 AND ROLE_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);

        roleManager.removeUsers("/USERS", "user");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 AND ROLE_ID=2");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);
    }

    @Test
    public void testRemoveGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);

        roleManager.removeGroups("/ADMINS", "/ADMINS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 0);
    }

    @Test
    public void testHasRole() throws Exception {
        boolean result = roleManager.hasRole("/USERS");
        assertTrue(result);

        result = roleManager.hasRole("nonexistedrole");
        assertFalse(result);
    }

    @Test(expected = RoleNotFoundException.class)
    public void testFindRole() throws Exception {
        Role role = roleManager.findRole("/USERS");
        assertNotNull(role);

        roleManager.findRole("nonexistedrole");
    }

    @Test
    public void testFindRoles() throws Exception {
        Page<Role> roles = roleManager.findRoles("nonexistedrole", new PageRequest(0, 10));
        assertFalse(roles.hasContent());
    }
}