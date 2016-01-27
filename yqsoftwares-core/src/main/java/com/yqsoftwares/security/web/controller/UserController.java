package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by Administrator on 2015-12-14.
 */
@RestController
@RequestMapping(value = "/security/user")
public class UserController {
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Page<User> findUsers(@RequestBody ModelMap inputs, @PageableDefault Pageable pageable) {
        // search by username like
        String username = (String) inputs.get("username");
        return userManager.findUsers(username, pageable);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addUser(@RequestBody ModelMap inputs) {
        User entity = (User) inputs.get("user");
        String[] groups = (String[]) inputs.get("groups");
        String[] roles = (String[]) inputs.get("roles");

        userManager.addUser(entity, Arrays.asList(groups), Arrays.asList(roles));

        return true;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean updateUser(@RequestBody ModelMap inputs) {
        User entity = (User) inputs.get("user");
        String[] groups = (String[]) inputs.get("groups");
        String[] roles = (String[]) inputs.get("roles");

        userManager.updateUser(entity, Arrays.asList(groups), Arrays.asList(roles));

        return true;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Page<Group> findAllGroups(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        return userManager.findAllGroups(pageable);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Page<Role> findAllRoles(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        return userManager.findAllRoles(pageable);
    }

    @RequestMapping(value = "/{username}/{enabled}", method = RequestMethod.POST)
    public boolean enableUser(@RequestParam String username, @RequestParam boolean enabled) {
        User user = userManager.findUser(username);
        user.setEnabled(enabled);

        userManager.updateUser(user);

        return true;
    }
}
