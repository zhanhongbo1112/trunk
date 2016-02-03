package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.GroupManager;
import com.yqsoftwares.security.core.Role;
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
@RequestMapping(value = "/security/group")
public class GroupController {
    @Autowired
    private GroupManager groupManager;

    @RequestMapping(method = RequestMethod.POST)
    public Page<Group> find(@RequestBody String path, @PageableDefault Pageable pageable) {
        // search by username like
        return groupManager.findGroups(path, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean update(@RequestBody Group group) {
        if (group.isNew()) {
            groupManager.addGroup(group);
        } else {
            groupManager.updateGroup(group);
        }

        return true;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public boolean addUses(@RequestBody OneToManyHolder<String, String> holder) {
        groupManager.addUsers(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public boolean updateUsers(@RequestBody OneToManyHolder<String, String> holder) {
        groupManager.updateUsers(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
    public boolean addRoles(@RequestBody OneToManyHolder<String, String> holder) {
        groupManager.addRoles(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/roles/update", method = RequestMethod.POST)
    public boolean updateRoles(@RequestBody OneToManyHolder<String, String> holder) {
        groupManager.updateRoles(holder.getOne(), holder.getMany());

        return true;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Page<User> findAllUsers(@PageableDefault Pageable pageable) {
        return groupManager.findAllUsers(pageable);
    }

    @RequestMapping(value = "/users/", method = RequestMethod.POST)
    public List<User> findUsers(@RequestBody String path) {
        return groupManager.findGroupUsers(path);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Page<Role> findAllRoles(@PageableDefault Pageable pageable) {
        return groupManager.findAllRoles(pageable);
    }

    @RequestMapping(value = "/roles/", method = RequestMethod.POST)
    public List<Role> findRoles(@RequestBody String path) {
        return groupManager.findGroupRoles(path);
    }
}
