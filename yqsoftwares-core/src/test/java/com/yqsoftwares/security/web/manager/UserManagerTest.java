package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    //@Test
    @Sql(scripts = {"testAddUser.sql"})
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
    @Sql(scripts = {"testUpdateUser.sql"})
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

    //@Test
    public void testFindUsers() throws Exception {
        fail("not yet implemented");
    }

    //@Test
    public void testFindAllGroups() throws Exception {
        fail("not yet implemented");
    }

    // @Test
    public void testFindAllRoles() throws Exception {
        fail("not yet implemented");
    }

    //@Test
    public void testUpdateState() throws Exception {
        fail("not yet implemented");
    }

    //@Test
    public void testFindUser() throws Exception {
        fail("not yet implemented");
    }
}