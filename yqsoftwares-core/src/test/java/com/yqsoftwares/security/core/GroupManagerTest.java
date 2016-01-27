package com.yqsoftwares.security.core;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.*;
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

import java.util.Arrays;

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
@WithUserDetails(value="supervisor")
public class GroupManagerTest {
    @Autowired
    private GroupManager groupManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test(expected = GroupExistsException.class)
    public void testAddGroup() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP", "PATH='/USERS/AGENTS'");
        assertTrue(count == 0);

        Group entity = new Group();
        entity.setPath("/USERS/AGENTS");
        entity.setAlias("AGENTS");
        entity.setDescription("Agent Group");

        groupManager.addGroup(entity);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP", "PATH='/USERS/AGENTS'");
        assertTrue(count == 1);

        entity = new Group();
        entity.setPath("/USERS/AGENTS");
        entity.setAlias("AGENTS");
        entity.setDescription("Agent Group");

        groupManager.addGroup(entity);
    }

    @Test
    public void testAddUsers() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "GROUP_ID=1 and USER_ID=2");
        assertTrue(count == 0);

        String[] inUsers = new String[]{"user"}; // USER_ID = 2
        groupManager.addUsers("/ADMINS", inUsers);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "GROUP_ID=1 and USER_ID=2");
        assertTrue(count == 1);
    }

    @Test
    public void testAddRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=2 and ROLE_ID=1");
        assertTrue(count == 0);

        String[] inRoles = new String[]{"/ADMINS"}; // ROLE_ID = 1
        groupManager.addRoles("/USERS", inRoles);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=2 and ROLE_ID=1");
        assertTrue(count == 1);
    }

    @Test
    public void testUpdateGroup() throws Exception {
        Group group = groupManager.findGroup("/ADMINS");
        assertEquals(group.getAlias(), "ADMINS");
        assertEquals(group.getDescription(), "ADMINS");

        group.setAlias("ADMINS_MODIFIED");
        group.setDescription("ADMINS_D_MODIFIED");
        groupManager.updateGroup(group);

        group = groupManager.findGroup("/ADMINS");
        assertEquals(group.getAlias(), "ADMINS_MODIFIED");
        assertEquals(group.getDescription(), "ADMINS_D_MODIFIED");
    }

    @Test
    public void testUpdateUsers() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "GROUP_ID=2");
        assertTrue(count == 2);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 AND GROUP_ID=2");
        assertTrue(count == 1);

        groupManager.updateUsers("/USERS", "admin");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 AND GROUP_ID=2");
        assertTrue(count == 0);
    }

    @Test
    public void testUpdateRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=2");
        assertTrue(count == 0);

        groupManager.updateRoles("/ADMINS", "/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);
    }

    @Test
    public void testRemoveGroup() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP", "PATH='/USERS'");
        assertTrue(count == 1);

        groupManager.removeGroup("/USERS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP", "PATH='/USERS'");
        assertTrue(count == 0);
    }

    @Test
    public void testRemoveUsers() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 AND GROUP_ID=2");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);

        groupManager.removeUsers("/USERS", "user");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 AND GROUP_ID=2");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 AND GROUP_ID=2");
        assertTrue(count == 1);
    }

    @Test
    public void testRemoveRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);

        groupManager.removeRoles("/ADMINS", "/ADMINS");

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 0);
    }

    @Test
    public void testAddGroupWithUsersAndRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP", "PATH='/USERS/AGENTS'");
        assertTrue(count == 0);

        Group entity = new Group();
        entity.setPath("/USERS/AGENTS");
        entity.setAlias("AGENTS");
        entity.setDescription("Agent Group");

        String[] inUsers = new String[]{"user"};
        String[] inRoles = new String[]{"/USERS"};

        groupManager.addGroup(entity, Arrays.asList(inUsers), Arrays.asList(inRoles));

        Group createdGroup = groupManager.findGroup("/USERS/AGENTS");
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "GROUP_ID=" + createdGroup.getId());
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=" + createdGroup.getId());
        assertTrue(count == 1);
    }

    @Test
    public void testUpdateGroupWithUsersAndRoles() throws Exception {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 and GROUP_ID=1");
        assertTrue(count == 0);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 and GROUP_ID=1");
        assertTrue(count == 1);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=2");
        assertTrue(count == 0);

        Group entity = groupManager.findGroup("/ADMINS");

        String[] inUsers = new String[]{"user"};
        String[] inRoles = new String[]{"/USERS"};

        groupManager.updateGroup(entity, Arrays.asList(inUsers), Arrays.asList(inRoles));

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=2 and GROUP_ID=1");
        assertTrue(count == 1);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_USER_GROUPS", "USER_ID=1 and GROUP_ID=1");
        assertTrue(count == 0);

        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=1");
        assertTrue(count == 0);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "SEC_GROUP_ROLES", "GROUP_ID=1 AND ROLE_ID=2");
        assertTrue(count == 1);
    }

    @Test
    public void testHasGroup() throws Exception {
        boolean result = groupManager.hasGroup("/USERS");
        assertTrue(result);

        result = groupManager.hasGroup("nonexistedgroup");
        assertFalse(result);
    }

    @Test(expected = GroupNotFoundException.class)
    public void testFindGroup() throws Exception {
        Group group = groupManager.findGroup("/USERS");
        assertNotNull(group);

        groupManager.findGroup("nonexistedgroup");
    }

    @Test
    public void testFindGroups() throws Exception {
        Page<Group> groups = groupManager.findGroups("nonexistedgroup", new PageRequest(0, 10));
        assertFalse(groups.hasContent());
    }

    @Test
    public void testFindAllUsers() throws Exception {
        Page<User> users = groupManager.findAllUsers(new PageRequest(0, 10));
        assertTrue(!users.getContent().isEmpty());
    }

    @Test
    public void testFindAllRoles() throws Exception {
        Page<Role> roles = groupManager.findAllRoles(new PageRequest(0, 10));
        assertTrue(!roles.getContent().isEmpty());
    }
}