package com.yqboots.project.security.core;

import com.yqboots.project.security.Application;
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

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015-12-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Sql(scripts = "00_init.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(scripts = "01_destroy.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@WithUserDetails(value="supervisor")
public class UserManagerTest {
    @Autowired
    private UserManager userManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testAddUser() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER", "USERNAME='user1'");
        assertTrue(count == 0);

        User entity = new User();
        entity.setUsername("user1");
        entity.setPassword("password");
        entity.setEnabled(true);

        userManager.addUser(entity);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER", "USERNAME='user1'");
        assertTrue(count == 1);
    }

    @Test
    public void testAddGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2");
        assertTrue(count == 1);

        String[] inGroups = new String[]{"/ADMINS"};
        userManager.addGroups("user", inGroups);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2");
        assertTrue(count == 2);
    }

    @Test
    public void testAddRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2");
        assertTrue(count == 1);

        String[] inRoles = new String[]{"/ADMINS"};
        userManager.addRoles("user", inRoles);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2");
        assertTrue(count == 2);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = userManager.findUser("user");
        assertEquals(user.isEnabled(), true);

        user.setEnabled(false);
        userManager.updateUser(user);

        user = userManager.findUser("user");
        assertEquals(user.isEnabled(), false);
    }

    @Test
    public void testUpdateGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1");
        assertTrue(count == 2);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=1");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        userManager.updateGroups("admin", "/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=1");
        assertTrue(count == 0);
    }

    @Test
    public void testUpdateRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1");
        assertTrue(count == 2);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);

        userManager.updateRoles("admin", "/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=1");
        assertTrue(count == 0);
    }

    @Test
    public void testRemoveUser() throws Exception {
        User user = userManager.findUser("user");
        assertTrue(user.isEnabled());

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER", "USERNAME='user'");
        assertTrue(count == 1);

        userManager.removeUser("user");

        // yq.security.user.disabled-when-removing=true
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER", "USERNAME='user'");
        assertTrue(count == 1);

        user = userManager.findUser("user");
        assertFalse(user.isEnabled());
    }

    @Test
    public void testRemoveGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1");
        assertTrue(count == 2);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=1");
        assertTrue(count == 1);

        userManager.removeGroups("admin", "/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=1");
        assertTrue(count == 1);
    }

    @Test
    public void testRemoveRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1");
        assertTrue(count == 2);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);

        userManager.removeRoles("admin", "/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=2");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);
    }

    @Test
    public void testHasUser() throws Exception {
        boolean result = userManager.hasUser("user");
        assertTrue(result);

        result = userManager.hasUser("nonexisteduser");
        assertFalse(result);
    }

    @Test
    public void testFindUsers() throws Exception {
        Page<User> users = userManager.findUsers("nonexisteduser", new PageRequest(0, 10));
        assertFalse(users.hasContent());

        users = userManager.findUsers("admin", new PageRequest(0, 10));
        assertTrue(users.hasContent());
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUser() throws Exception {
        User user = userManager.findUser("user");
        assertNotNull(user);

        userManager.findUser("nonexisteduser");
    }

    @Test
    public void testFindAllGroups() throws Exception {
        Page<Group> groups = userManager.findAllGroups(new PageRequest(0, 10));
        assertTrue(!groups.getContent().isEmpty());
    }

    @Test
    public void testFindUserGroups() throws Exception {
        List<Group> groups = userManager.findUserGroups("supervisor");
        assertTrue(!groups.isEmpty());
    }

    @Test
    public void testFindAllRoles() throws Exception {
        Page<Role> roles = userManager.findAllRoles(new PageRequest(0, 10));
        assertTrue(!roles.getContent().isEmpty());
    }

    @Test
    public void testFindUserRoles() throws Exception {
        List<Role> roles = userManager.findUserRoles("supervisor");
        assertTrue(roles.isEmpty());
    }

}