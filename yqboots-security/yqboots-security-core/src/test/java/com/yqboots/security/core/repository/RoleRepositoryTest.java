package com.yqboots.security.core.repository;

import com.yqboots.security.Application;
import com.yqboots.security.core.Role;
import com.yqboots.security.util.DBUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015-12-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByPath() throws Exception {
        Role role = roleRepository.findByPath("nonexistedpath");
        assertNull(role);
    }

    @Test
    public void testDelete() throws Exception {
        roleRepository.delete("nonexistedpath");
    }

    @Test
    public void testFindByUsersUsername() throws Exception {
        List<Role> roles = roleRepository.findByUsersUsername("nonexistedusername");
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindByUsersUsernameIn() throws Exception {
        Set<String> usernames = new HashSet<>();
        usernames.add("nonexistedusername");
        List<Role> roles = roleRepository.findByUsersUsernameIn(usernames);
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindByGroupsPath() throws Exception {
        List<Role> roles = roleRepository.findByGroupsPath("nonexistedgrouppath");
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindByGroupsPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<Role> roles = roleRepository.findByGroupsPathIn(paths);
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindByPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<Role> roles = roleRepository.findByPathIn(paths);
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindByPathLikeIgnoreCase() throws Exception {
        List<Role> roles = roleRepository.findByPathLikeIgnoreCase(DBUtils.wildcard("none"));
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testExists() throws Exception {
        boolean result = roleRepository.exists("nonexitedpath");
        assertFalse(result);
    }
}