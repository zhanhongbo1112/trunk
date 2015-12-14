package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

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
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByUsernameIn() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByRolesPath() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByGroupsPath() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByUsernameLikeIgnoreCase() throws Exception {
        Assert.fail("not yet implemented");
    }
}