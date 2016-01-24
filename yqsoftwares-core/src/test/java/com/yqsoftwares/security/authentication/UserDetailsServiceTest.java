package com.yqsoftwares.security.authentication;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016-01-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Sql
public class UserDetailsServiceTest {
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() throws Exception {
        User result = (User) userDetailsService.loadUserByUsername("admin");
        assertNotNull(result);
        assertTrue(result.getAuthorities().size() == 2);
    }
}