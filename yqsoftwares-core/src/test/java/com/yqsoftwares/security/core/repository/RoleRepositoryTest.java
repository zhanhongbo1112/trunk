package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.Role;
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
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByPath() throws Exception {
        Role role = roleRepository.findByPath("nonexistedpath");
        Assert.assertNull(role);
    }

    @Test
    public void testDelete() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByUsersUsername() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByGroupsPath() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByGroupsPathIn() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByPathIn() throws Exception {
        Assert.fail("not yet implemented");
    }

    @Test
    public void testFindByPathLikeIgnoreCase() throws Exception {
        Assert.fail("not yet implemented");
    }
}