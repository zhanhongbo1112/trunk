package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.UserManager;
import com.yqsoftwares.security.web.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015-12-14.
 */
@RestController
@RequestMapping(value = "/security/user")
public class UserController {
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Page<User> findUsers(@PathVariable String username, @PageableDefault Pageable pageable) {
        // search by username like
        return userManager.findUsers(username, pageable);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addUser(@RequestBody UserContext context) {
        final User entity = context.getUser();
        final String[] groups = context.getGroups();
        final String[] roles = context.getRoles();

        userManager.addUser(entity, Arrays.asList(groups), Arrays.asList(roles));

        return true;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean updateUser(@RequestBody UserContext context) {
        final User entity = context.getUser();
        final String[] groups = context.getGroups();
        final String[] roles = context.getRoles();

        userManager.updateUser(entity, Arrays.asList(groups), Arrays.asList(roles));

        return true;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Page<Group> findAllGroups(@PageableDefault Pageable pageable) {
        return userManager.findAllGroups(pageable);
    }

    @RequestMapping(value = "/groups/{username}", method = RequestMethod.GET)
    public List<Group> findUserGroups(@PathVariable String username) {
        return userManager.findUserGroups(username);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Page<Role> findAllRoles(@PageableDefault Pageable pageable) {
        return userManager.findAllRoles(pageable);
    }

    @RequestMapping(value = "/roles/{username}", method = RequestMethod.GET)
    public List<Role> findUserRoles(@PathVariable String username) {
        return userManager.findUserRoles(username);
    }
}
