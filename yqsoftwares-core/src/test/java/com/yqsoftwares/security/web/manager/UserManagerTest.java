package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015-12-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class UserManagerTest {
    @Autowired
    private UserManager userManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @After
    public void destroy() throws Exception {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("com/yqsoftwares/security/web/manager/destroy.sql"));
    }

    @Test
    @Sql(scripts = {"init.sql", "testAddUser.sql"})
    public void testAddUser() throws Exception {
        User entity = new User();
        entity.setUsername("user1");
        entity.setPassword("password");
        entity.setEnabled(true);

        String[] inGroups = new String[]{"/AGENTS"};
        String[] inRoles = new String[]{"/AGENTS"};

        userManager.addUser(entity, Arrays.asList(inGroups), Arrays.asList(inRoles));

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=3");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=3");
        assertTrue(count == 1);
    }

    @Test
    @Sql(scripts = {"init.sql", "testUpdateUser.sql"})
    public void testUpdateUser() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=3 and GROUP_ID=1");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=3 AND ROLE_ID=1");
        assertTrue(count == 1);

        User entity = userManager.findUser("user1");

        String[] inGroups = new String[]{"/AGENTS"};
        String[] inRoles = new String[]{"/AGENTS"};

        userManager.updateUser(entity, Arrays.asList(inGroups), Arrays.asList(inRoles));

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=3 and GROUP_ID=3");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=3 AND ROLE_ID=3");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=3 and GROUP_ID=1");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_ROLES", "USER_ID=3 AND ROLE_ID=1");
        assertTrue(count == 0);
    }

    @Test
    @Sql(scripts = {"init.sql"})
    public void testFindUsers() throws Exception {
        Page<User> users = userManager.findUsers("nonexisteduser", new PageRequest(0, 10));
        assertFalse(users.hasContent());
    }

    @Test
    @Sql(scripts = {"init.sql"})
    public void testFindAllGroups() throws Exception {
        List<Group> groups = userManager.findAllGroups(new PageRequest(0, 10));
        assertTrue(groups.size() == 2);
    }

    @Test
    @Sql(scripts = {"init.sql"})
    public void testFindAllRoles() throws Exception {
        List<Role> roles = userManager.findAllRoles(new PageRequest(0, 10));
        assertTrue(roles.size() == 2);
    }

    @Test
    @Sql(scripts = {"init.sql"})
    public void testUpdateState() throws Exception {
        User user = userManager.findUser("user");
        assertTrue(user.isEnabled());
        userManager.updateState("user", false);

        user = userManager.findUser("user");
        assertTrue(!user.isEnabled());
    }

    @Test
    @Sql(scripts = {"init.sql"})
    public void testFindUser() throws Exception {
        User user = userManager.findUser("user");
        assertNotNull(user);
    }
}