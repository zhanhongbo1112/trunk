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
package com.yqboots.project.security.core;

import com.yqboots.project.security.core.audit.SecurityAudit;
import com.yqboots.project.security.core.audit.annotation.Auditable;
import com.yqboots.project.security.core.repository.GroupRepository;
import com.yqboots.project.security.core.repository.RoleRepository;
import com.yqboots.project.security.core.repository.UserRepository;
import com.yqboots.project.security.util.DBUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Role manager implementation.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Service
@Transactional(readOnly = true)
public class RoleManagerImpl implements RoleManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_ROLE)
    public void addRole(Role role) throws RoleExistsException {
        Assert.isTrue(role.isNew());
        Assert.hasText(role.getPath());
        if (roleRepository.exists(role.getPath())) {
            throw new RoleExistsException(role.getPath());
        }

        roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_USERS_TO_ROLE)
    public void addUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        for (User user : users) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_GROUPS_TO_ROLE)
    public void addGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        for (Group group : groups) {
            group.getRoles().add(role);
            groupRepository.save(group);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLE)
    public void updateRole(Role role) throws RoleNotFoundException {
        Assert.isTrue(!role.isNew());
        Assert.hasText(role.getPath());

        Role entity = roleRepository.findByPath(role.getPath());
        if (entity == null) {
            throw new RoleNotFoundException(role.getPath());
        }

        entity.setAlias(role.getAlias());
        entity.setDescription(role.getDescription());

        roleRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USERS_OF_ROLE)
    public void updateUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role entity = roleRepository.findByPath(path);
        if (entity == null) {
            throw new RoleNotFoundException(path);
        }

        for (User user : entity.getUsers()) {
            user.getRoles().remove(entity);
            userRepository.saveAndFlush(user);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        for (User user : users) {
            user.getRoles().add(entity);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_GROUPS_OF_ROLE)
    public void updateGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role entity = roleRepository.findByPath(path);
        if (entity == null) {
            throw new RoleNotFoundException(path);
        }

        for (Group group : entity.getGroups()) {
            group.getRoles().remove(entity);
            groupRepository.saveAndFlush(group);
        }

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        for (Group group : groups) {
            group.getRoles().add(entity);
            groupRepository.save(group);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLE)
    public void removeRole(Long id) throws RoleNotFoundException {
        Assert.notNull(id);

        Role role = roleRepository.findOne(id);
        if (role == null) {
            throw new RoleNotFoundException(Long.toString(id));
        }

        List<User> users = userRepository.findByRolesPath(role.getPath());
        for (User user : users) {
            user.getRoles().remove(role);
            userRepository.saveAndFlush(user);
        }
        List<Group> groups = groupRepository.findByRolesPath(role.getPath());
        for (Group group : groups) {
            group.getRoles().remove(role);
            groupRepository.saveAndFlush(group);
        }

        roleRepository.delete(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLE)
    public void removeRole(String path) throws RoleNotFoundException {
        Assert.hasText(path);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<User> users = userRepository.findByRolesPath(role.getPath());
        for (User user : users) {
            user.getRoles().remove(role);
            userRepository.saveAndFlush(user);
        }
        List<Group> groups = groupRepository.findByRolesPath(role.getPath());
        for (Group group : groups) {
            group.getRoles().remove(role);
            groupRepository.saveAndFlush(group);
        }

        roleRepository.delete(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USERS_FROM_ROLE)
    public void removeUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<User> users = userRepository.findByRolesPath(role.getPath());
        users.stream().filter(user -> ArrayUtils.contains(usernames, user.getUsername())).forEach(user -> {
            user.getRoles().remove(role);
            userRepository.save(user);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUPS_FROM_ROLE)
    public void removeGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<Group> groups = groupRepository.findByRolesPath(role.getPath());
        groups.stream().filter(group -> ArrayUtils.contains(groupPaths, group.getPath())).forEach(group -> {
            group.getRoles().remove(role);
            groupRepository.save(group);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRole(String path) {
        return roleRepository.exists(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findRole(Long id) throws RoleNotFoundException {
        Assert.notNull(id);

        Role role = roleRepository.findOne(id);
        if (role == null) {
            throw new RoleNotFoundException(Long.toString(id));
        }

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findRole(String path) throws RoleNotFoundException {
        Assert.hasText(path);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Role> findRoles(String pathFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(pathFilter);
        return roleRepository.findByPathLikeIgnoreCase(filter, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findRoleUsers(String path) {
        return userRepository.findByRolesPath(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Group> findAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Group> findRoleGroups(String path) {
        return groupRepository.findByRolesPath(path);
    }
}
