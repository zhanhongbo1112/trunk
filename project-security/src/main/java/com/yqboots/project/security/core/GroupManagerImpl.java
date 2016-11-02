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
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Group manager implementation.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Service
@Transactional(readOnly = true)
public class GroupManagerImpl implements GroupManager {
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
    @Auditable(code = SecurityAudit.CODE_ADD_GROUP)
    public void addGroup(@P("group") Group group) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());
        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        groupRepository.save(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addGroup(final String path, final Long[] userIds, final Long[] roleIds) throws GroupExistsException {
        Assert.hasText(path);
        if (groupRepository.exists(path)) {
            throw new GroupExistsException(path);
        }

        Group group = new Group();
        group.setPath(path);

        if (ArrayUtils.isNotEmpty(userIds)) {
            group.setUsers(new HashSet<>(userRepository.findAll(Arrays.asList(userIds))));
        }

        if (ArrayUtils.isNotEmpty(roleIds)) {
            group.setRoles(new HashSet<>(roleRepository.findAll(Arrays.asList(roleIds))));
        }

        groupRepository.save(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_USERS_TO_GROUP)
    public void addUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        for (User user : users) {
            user.getGroups().add(group);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_ROLES_TO_GROUP)
    public void addRoles(String path, String... rolePaths) throws GroupExistsException {
        Assert.hasText(path);
        Assert.notEmpty(rolePaths);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!roles.isEmpty()) {
            group.getRoles().addAll(roles);
            groupRepository.save(group);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_GROUP)
    public void updateGroup(Group group) throws GroupNotFoundException {
        Assert.isTrue(!group.isNew());
        Assert.hasText(group.getPath());

        Group entity = groupRepository.findByPath(group.getPath());
        if (entity == null) {
            throw new GroupNotFoundException(group.getPath());
        }

        entity.setAlias(group.getAlias());
        entity.setDescription(group.getDescription());

        groupRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateGroup(final String path, final Long[] userIds, final Long[] roleIds) throws GroupNotFoundException {
        Assert.hasText(path);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        updateUsers(path, userIds);
        updateRoles(path, roleIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUsers(String path, Long... userIds) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(userIds);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        for (User user : entity.getUsers()) {
            user.getGroups().remove(entity);
            userRepository.saveAndFlush(user);
        }

        List<User> users = userRepository.findAll(Arrays.asList(userIds));
        for (User user : users) {
            user.getGroups().add(entity);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USERS_OF_GROUP)
    public void updateUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        for (User user : entity.getUsers()) {
            user.getGroups().remove(entity);
            userRepository.saveAndFlush(user);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        for (User user : users) {
            user.getGroups().add(entity);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateRoles(String path, Long... roleIds) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notNull(roleIds);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        entity.getRoles().clear();

        if (ArrayUtils.isNotEmpty(roleIds)) {
            final List<Role> roles = roleRepository.findAll(Arrays.asList(roleIds));
            if (!roles.isEmpty()) {
                entity.setRoles(new HashSet<>(roles));
            }
        }

        groupRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_GROUP)
    public void updateRoles(String path, String... rolePaths) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notNull(rolePaths);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        entity.getRoles().clear();
        final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!roles.isEmpty()) {
            entity.setRoles(new HashSet<>(roles));
        }

        groupRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUP)
    public void removeGroup(Long id) throws GroupNotFoundException {
        Assert.notNull(id);

        Group group = groupRepository.findOne(id);
        if (group == null) {
            throw new GroupNotFoundException(Long.toString(id));
        }

        List<User> users = userRepository.findByGroupsPath(group.getPath());
        for (User user : users) {
            user.getGroups().remove(group);
            userRepository.saveAndFlush(user);
        }

        groupRepository.delete(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUP)
    public void removeGroup(String path) throws GroupNotFoundException {
        Assert.hasText(path);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        List<User> users = userRepository.findByGroupsPath(group.getPath());
        for (User user : users) {
            user.getGroups().remove(group);
            userRepository.saveAndFlush(user);
        }

        groupRepository.delete(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USERS_FROM_GROUP)
    public void removeUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        List<User> users = userRepository.findByGroupsPath(group.getPath());
        users.stream().filter(user -> ArrayUtils.contains(usernames, user.getUsername())).forEach(user -> {
            user.getGroups().remove(group);
            userRepository.save(user);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_GROUP)
    public void removeRoles(String path, String... rolePaths) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(rolePaths);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!roles.isEmpty()) {
            group.getRoles().removeAll(roles);
            groupRepository.save(group);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGroup(String path) {
        return groupRepository.exists(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group findGroup(Long id) throws GroupNotFoundException {
        Assert.notNull(id);

        Group result = groupRepository.findOne(id);
        if (result == null) {
            throw new GroupNotFoundException(Long.toString(id));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group findGroup(String path) throws GroupNotFoundException {
        Assert.hasText(path);

        Group result = groupRepository.findByPath(path);
        if (result == null) {
            throw new GroupNotFoundException(path);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Group> findGroups(String pathFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(pathFilter);
        return groupRepository.findByPathLikeIgnoreCase(filter, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Group> findGroups(final Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
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
    public List<User> findGroupUsers(String path) {
        return userRepository.findByGroupsPath(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findGroupRoles(String path) {
        return roleRepository.findByGroupsPath(path);
    }
}
