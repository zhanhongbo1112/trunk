package com.yqboots.security.core.repository;

import com.yqboots.security.Application;
import com.yqboots.security.core.User;
import com.yqboots.security.util.DBUtils;
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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() throws Exception {
        User user = userRepository.findByUsername("nonexistedusername");
        Assert.assertNull(user);
    }

    @Test
    public void testDelete() throws Exception {
        userRepository.delete("noneusername");
    }

    @Test
    public void testFindByUsernameIn() throws Exception {
        Set<String> usernames = new HashSet<>();
        usernames.add("nonexistedusername");
        List<User> users = userRepository.findByUsernameIn(usernames);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByRolesPath() throws Exception {
        List<User> users = userRepository.findByRolesPath("nonexistedpath");
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByRolesPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<User> users = userRepository.findByRolesPathIn(paths);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByGroupsPath() throws Exception {
        List<User> users = userRepository.findByGroupsPath("nonexistedpath");
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByGroupsPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<User> users = userRepository.findByGroupsPathIn(paths);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByUsernameLikeIgnoreCase() throws Exception {
        List<User> users = userRepository.findByUsernameLikeIgnoreCase(DBUtils.wildcard("none"));
        assertTrue(users.isEmpty());
    }

    @Test
    public void testExists() throws Exception {
        boolean result = userRepository.exists("nonexitedpath");
        assertFalse(result);
    }
}