/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.project.security.web.controller;

import com.yqboots.project.security.core.Group;
import com.yqboots.project.security.core.User;
import com.yqboots.project.security.core.Role;
import com.yqboots.project.security.core.UserManager;
import com.yqboots.project.security.web.model.OneToManyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for {@link com.yqboots.project.security.core.User}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@RestController
@RequestMapping(value = "/project/security/user")
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
