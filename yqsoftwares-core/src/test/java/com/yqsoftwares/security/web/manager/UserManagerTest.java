package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.security.Security;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015-12-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
// not work
//@Sql(scripts = "init.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
//)
//@Sql(scripts = "destroy.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
//        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
//)
public class UserManagerTest {
    @Autowired
    private UserManager userManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("com/yqsoftwares/security/web/manager/init.sql"));

        User user = userManager.findUser("user");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), null));
    }

    @After
    public void destroy() throws Exception {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("com/yqsoftwares/security/web/manager/destroy.sql"));
    }

    @Test
    @Sql
    public void testAddUserWithGroupsAndRoles() throws Exception {
        User entity = new User();
        entity.setUsername("user1");
        entity.setPassword("password");
        entity.setEnabled(true);

        String[] inGroups = new String[]{"/AGENTS"};
        String[] inRoles = new String[]{"/AGENTS"};

        userManager.addUser(entity, Arrays.asList(inGroups), Arrays.asList(inRoles));

        User createdUser = userManager.findUser("user1");
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=" + createdUser.getId());
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=" + createdUser.getId());
        assertTrue(count == 1);
    }

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
    @Sql
    public void testAddGroups() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2");
        assertTrue(count == 1);

        String[] inGroups = new String[]{"/AGENTS"};
        userManager.addGroups("user", inGroups);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2");
        assertTrue(count == 2);
    }

    @Test
    @Sql
    public void testAddRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2");
        assertTrue(count == 1);

        String[] inRoles = new String[]{"/AGENTS"};
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
    @Sql
    public void testUpdateUserWithGroupsAndRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 and GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 AND ROLE_ID=2");
        assertTrue(count == 1);

        User entity = userManager.findUser("user");

        String[] inGroups = new String[]{"/AGENTS"};
        String[] inRoles = new String[]{"/AGENTS"};

        userManager.updateUser(entity, Arrays.asList(inGroups), Arrays.asList(inRoles));

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 and GROUP_ID=3");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 and GROUP_ID=2");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 AND ROLE_ID=3");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=2 AND ROLE_ID=2");
        assertTrue(count == 0);
    }

    @Test
    public void testFindUsers() throws Exception {
        Page<User> users = userManager.findUsers("nonexisteduser", new PageRequest(0, 10));
        assertFalse(users.hasContent());
    }

    @Test
    public void testFindAllGroups() throws Exception {
        Page<Group> groups = userManager.findAllGroups(new PageRequest(0, 10));
        assertTrue(groups.getContent().size() == 2);
    }

    @Test
    public void testFindAllRoles() throws Exception {
        Page<Role> roles = userManager.findAllRoles(new PageRequest(0, 10));
        assertTrue(roles.getContent().size() == 2);
    }

    @Test
    public void testFindUser() throws Exception {
        User user = userManager.findUser("user");
        assertNotNull(user);
    }

}