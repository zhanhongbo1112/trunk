package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByRolesPath() throws Exception {
        List<User> users = userRepository.findByRolesPath("nonexistedpath");
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByRolesPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<User> users = userRepository.findByRolesPathIn(paths);
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByGroupsPath() throws Exception {
        List<User> users = userRepository.findByGroupsPath("nonexistedpath");
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByGroupsPathIn() throws Exception {
        Set<String> paths = new HashSet<>();
        paths.add("nonexistedpath");
        List<User> users = userRepository.findByGroupsPathIn(paths);
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByUsernameLikeIgnoreCase() throws Exception {
        List<User> users = userRepository.findByUsernameLikeIgnoreCase(DBUtils.wildcard("none"));
        Assert.assertTrue(users.isEmpty());
    }
}