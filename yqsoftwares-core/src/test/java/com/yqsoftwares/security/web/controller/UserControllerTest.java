package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015-12-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
//@WebIntegrationTest
public class UserControllerTest {

//    RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testFindUsers() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testAddUser() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testUpdateUser() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testFindAllGroups() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testFindAllRoles() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testEnableUser() throws Exception {
        fail("not yet implemented");
    }
}