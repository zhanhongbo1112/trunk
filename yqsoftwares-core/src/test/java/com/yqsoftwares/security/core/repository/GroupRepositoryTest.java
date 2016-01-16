package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2015-12-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testFindByPath() throws Exception {
        Group group = groupRepository.findByPath("nonexistedpath");
        Assert.assertNull(group);
    }

    @Test
    public void testDelete() throws Exception {
        groupRepository.delete("nonexistedpath");
    }

    @Test
    public void testFindByUsersUsername() throws Exception {
        List<Group> groups = groupRepository.findByUsersUsername("nonexistedusername");
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testFindByUsersUsernameIn() throws Exception {
        Set<String> usernames = new HashSet<>();
        usernames.add("nonexistedusername");
        List<Group> groups = groupRepository.findByUsersUsernameIn(usernames);
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testFindByRolesPath() throws Exception {
        List<Group> groups = groupRepository.findByRolesPath("nonexistedpath");
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testFindByRolesPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<Group> groups = groupRepository.findByRolesPathIn(paths);
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testFindByPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<Group> groups = groupRepository.findByPathIn(paths);
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testFindByPathLikeIgnoreCase() throws Exception {
        List<Group> groups = groupRepository.findByPathLikeIgnoreCase(DBUtils.wildcard("none"));
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testExists() throws Exception {
        boolean result = groupRepository.exists("nonexitedpath");
        assertFalse(result);
    }
}