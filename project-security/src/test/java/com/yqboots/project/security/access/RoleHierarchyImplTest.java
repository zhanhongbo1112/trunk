package com.yqboots.project.security.access;

import com.yqboots.project.security.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class RoleHierarchyImplTest {

    @Test
    public void testGetReachableGrantedAuthorities() throws Exception {
        Assert.fail("not yet implemented");
    }
}