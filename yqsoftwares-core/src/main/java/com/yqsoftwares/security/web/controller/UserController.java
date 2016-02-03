package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.UserManager;
import com.yqsoftwares.web.model.OneToManyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2015-12-14.
 */
@RestController
@RequestMapping(value = "/security/user")
public class UserController {
    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.POST)
    public Page<User> find(@RequestBody String username, @PageableDefault Pageable pageable) {
        // search by username like
        return userManager.findUsers(username, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean update(@RequestBody User user) {
        if (user.isNew()) {
            userManager.addUser(user);
        } else {
            userManager.updateUser(user);
        }

        return true;
    }

    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    public boolean addGroups(@RequestBody OneToManyHolder<String, String> holder) {
        userManager.addGroups(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/groups/update", method = RequestMethod.POST)
    public boolean updateGroups(@RequestBody OneToManyHolder<String, String> holder) {
        userManager.updateGroups(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
    public boolean addRoles(@RequestBody OneToManyHolder<String, String> holder) {
        userManager.addRoles(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/roles/update", method = RequestMethod.POST)
    public boolean updateRoles(@RequestBody OneToManyHolder<String, String> holder) {
        userManager.updateRoles(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Page<Group> findAllGroups(@PageableDefault Pageable pageable) {
        return userManager.findAllGroups(pageable);
    }

    @RequestMapping(value = "/groups/", method = RequestMethod.POST)
    public List<Group> findGroups(@RequestBody String username) {
        return userManager.findUserGroups(username);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Page<Role> findAllRoles(@PageableDefault Pageable pageable) {
        return userManager.findAllRoles(pageable);
    }

    @RequestMapping(value = "/roles/", method = RequestMethod.POST)
    public List<Role> findRoles(@RequestBody String username) {
        return userManager.findUserRoles(username);
    }
}
