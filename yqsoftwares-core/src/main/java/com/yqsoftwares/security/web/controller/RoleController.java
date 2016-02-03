package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.RoleManager;
import com.yqsoftwares.security.core.User;
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
@RequestMapping(value = "/security/role")
public class RoleController {
    @Autowired
    private RoleManager roleManager;

    @RequestMapping(method = RequestMethod.POST)
    public Page<Role> find(@RequestBody String path, @PageableDefault Pageable pageable) {
        // search by username like
        return roleManager.findRoles(path, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean update(@RequestBody Role role) {
        if (role.isNew()) {
            roleManager.addRole(role);
        } else {
            roleManager.updateRole(role);
        }

        return true;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public boolean addRoles(@RequestBody OneToManyHolder<String, String> holder) {
        roleManager.addUsers(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public boolean updateRoles(@RequestBody OneToManyHolder<String, String> holder) {
        roleManager.updateUsers(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    public boolean addGroups(@RequestBody OneToManyHolder<String, String> holder) {
        roleManager.addGroups(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/groups/update", method = RequestMethod.POST)
    public boolean updateGroups(@RequestBody OneToManyHolder<String, String> holder) {
        roleManager.updateGroups(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Page<User> findAllUsers(@PageableDefault Pageable pageable) {
        return roleManager.findAllUsers(pageable);
    }

    @RequestMapping(value = "/users/", method = RequestMethod.POST)
    public List<User> findUsers(@RequestBody String path) {
        return roleManager.findRoleUsers(path);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Page<Group> findAllGroups(@PageableDefault Pageable pageable) {
        return roleManager.findAllGroups(pageable);
    }

    @RequestMapping(value = "/groups/", method = RequestMethod.POST)
    public List<Group> findGroups(@RequestBody String path) {
        return roleManager.findRoleGroups(path);
    }
}
