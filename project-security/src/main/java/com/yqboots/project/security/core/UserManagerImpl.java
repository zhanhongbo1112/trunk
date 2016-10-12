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

import com.yqboots.project.security.autoconfigure.SecurityProperties;
import com.yqboots.project.security.core.audit.SecurityAudit;
import com.yqboots.project.security.core.audit.annotation.Auditable;
import com.yqboots.project.security.core.repository.GroupRepository;
import com.yqboots.project.security.core.repository.RoleRepository;
import com.yqboots.project.security.core.repository.UserRepository;
import com.yqboots.project.security.util.DBUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * User manager implementation.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Service
@Transactional(readOnly = true)
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    @Autowired
    private SecurityProperties properties;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_USER)
    public void addUser(User user) throws UserExistsException {
        Assert.isTrue(user.isNew());
        Assert.hasText(user.getUsername());
        if (userRepository.exists(user.getUsername())) {
            throw new UserExistsException(user.getUsername());
        }

        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            password = properties.getUser().getPasswordDefault();
        }
        // encrypt the password
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_GROUPS_TO_USER)
    public void addGroups(String username, String... groupPaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(groupPaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        if (!groups.isEmpty()) {
            user.getGroups().addAll(groups);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_ROLES_TO_USER)
    public void addRoles(String username, String... rolePaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(rolePaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!roles.isEmpty()) {
            user.getRoles().addAll(roles);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USER)
    public void updateUser(User user) throws UserNotFoundException {
        Assert.isTrue(!user.isNew());
        Assert.hasText(user.getUsername());

        User entity = userRepository.findByUsername(user.getUsername());
        if (entity == null) {
            throw new UserNotFoundException(user.getUsername());
        }

        entity.setEnabled(user.isEnabled());

        userRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_GROUPS_OF_USER)
    public void updateGroups(String username, String... groupPaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(groupPaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        user.getGroups().clear();
        final List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        if (!groups.isEmpty()) {
            user.setGroups(new HashSet<>(groups));
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_USER)
    public void updateRoles(String username, String... rolePaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(rolePaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        user.getRoles().clear();
        final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!roles.isEmpty()) {
            user.setRoles(new HashSet<>(roles));
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USER)
    public void removeUser(String username) throws UserNotFoundException {
        Assert.hasText(username);

        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (properties.getUser().isDisabledWhenRemoving()) {
            user.setEnabled(false);
            userRepository.save(user);
        } else {
            userRepository.delete(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUPS_FROM_USER)
    public void removeGroups(String username, String... groupPaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(groupPaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        if (!groups.isEmpty()) {
            user.getGroups().removeAll(groups);
            userRepository.save(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_USER)
    public void removeRoles(String username, String... rolePaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(rolePaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Role> inRoles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        if (!inRoles.isEmpty()) {
            user.getRoles().removeAll(inRoles);
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasUser(String username) {
        return userRepository.exists(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUser(String username) throws UserNotFoundException {
        Assert.hasText(username);

        User result = userRepository.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException(username);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findUsers(String usernameFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(usernameFilter);
        return userRepository.findByUsernameLikeIgnoreCase(filter, pageable);
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
    public List<Group> findUserGroups(String username) {
        return groupRepository.findByUsersUsername(username);
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
    public List<Role> findUserRoles(String username) {
        return roleRepository.findByUsersUsername(username);
    }
}
