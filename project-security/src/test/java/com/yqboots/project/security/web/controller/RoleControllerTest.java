package com.yqboots.project.security.web.controller;

import com.yqboots.project.security.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2016-02-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@WithUserDetails(value = "supervisor")
public class RoleControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testFind() throws Exception {
        this.mockMvc.perform(post("/security/role").content("/SUPERVISOR").param("page", "0").param("size", "15").param("sort", "path,desc")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    // @Test
    public void testUpdate() throws Exception {
        fail("not yet implemented");
    }

    // @Test
    public void testAddRoles() throws Exception {
        fail("not yet implemented");
    }

    // @Test
    public void testUpdateRoles() throws Exception {
        fail("not yet implemented");
    }

    // @Test
    public void testAddGroups() throws Exception {
        fail("not yet implemented");
    }

    // @Test
    public void testUpdateGroups() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testFindAllUsers() throws Exception {
        this.mockMvc.perform(get("/security/role/users").param("sort", "username")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testFindUsers() throws Exception {
        this.mockMvc.perform(post("/security/role/users/").content("/SUPERVISOR")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testFindAllGroups() throws Exception {
        this.mockMvc.perform(get("/security/role/groups").param("sort", "path")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testFindGroups() throws Exception {
        this.mockMvc.perform(post("/security/role/groups/").content("/SUPERVISOR")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}