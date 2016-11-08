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
package com.yqboots.security.core;

import com.yqboots.core.util.DBUtils;
import com.yqboots.security.autoconfigure.SecurityProperties;
import com.yqboots.security.core.audit.SecurityAudit;
import com.yqboots.security.core.audit.annotation.Auditable;
import com.yqboots.security.core.repository.GroupRepository;
import com.yqboots.security.core.repository.RoleRepository;
import com.yqboots.security.core.repository.UserRepository;
import org.apache.commons.lang3.ArrayUtils;
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
    public void addUser(final User user) throws UserExistsException {
        Assert.isTrue(user.isNew());
        Assert.hasText(user.getUsername());
        if (userRepository.exists(user.getUsername())) {
            throw new UserExistsException(user.getUsername());
        }

        user.setPassword(getPasswordDefault(user.getPassword()));

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USER)
    public void updateUser(final User user) throws UserNotFoundException {
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
    public void updateGroups(final String username, final String... groupPaths) throws UserNotFoundException {
        Assert.hasText(username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        // if groupPaths is empty, will remove all
        user.getGroups().clear();
        if (ArrayUtils.isNotEmpty(groupPaths)) {
            final List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
            if (!groups.isEmpty()) {
                user.setGroups(new HashSet<>(groups));
            }
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_GROUPS_OF_USER)
    public void updateGroups(final String username, final Long... groupIds) throws UserNotFoundException {
        Assert.hasText(username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        user.getGroups().clear();
        if (ArrayUtils.isNotEmpty(groupIds)) {
            final List<Group> groups = groupRepository.findAll(Arrays.asList(groupIds));
            if (!groups.isEmpty()) {
                user.setGroups(new HashSet<>(groups));
            }
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_USER)
    public void updateRoles(final String username, final String... rolePaths) throws UserNotFoundException {
        Assert.hasText(username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        user.getRoles().clear();
        if (ArrayUtils.isNotEmpty(rolePaths)) {
            final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
            if (!roles.isEmpty()) {
                user.setRoles(new HashSet<>(roles));
            }
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLES_OF_USER)
    public void updateRoles(final String username, Long... roleIds) throws UserNotFoundException {
        Assert.hasText(username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        user.getRoles().clear();
        if (ArrayUtils.isNotEmpty(roleIds)) {
            final List<Role> roles = roleRepository.findAll(Arrays.asList(roleIds));
            if (!roles.isEmpty()) {
                user.setRoles(new HashSet<>(roles));
            }
        }

        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USER)
    public void removeUser(final String username) throws UserNotFoundException {
        Assert.hasText(username);

        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (properties.getUser().isDisabledWhenRemoving()) {
            user.setEnabled(false);
            userRepository.save(user);
        } else {
            // TODO: test if remove all related groups and roles
            userRepository.delete(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USER)
    public void removeUser(final Long id) throws UserNotFoundException {
        Assert.notNull(id);

        final User user = userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(Long.toString(id));
        }

        if (properties.getUser().isDisabledWhenRemoving()) {
            user.setEnabled(false);
            userRepository.save(user);
        } else {
            // remove physically
            userRepository.delete(user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUPS_FROM_USER)
    public void removeGroups(final String username, final String... groupPaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(groupPaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        groups.stream().filter(group -> ArrayUtils.contains(groupPaths, group.getPath()))
                .forEach(group -> user.getGroups().remove(group));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_GROUPS_FROM_USER)
    public void removeGroups(final String username, final Long... groupIds) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notEmpty(groupIds);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Group> groups = groupRepository.findAll(Arrays.asList(groupIds));
        groups.stream().filter(group -> ArrayUtils.contains(groupIds, group.getId()))
                .forEach(group -> user.getGroups().remove(group));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_USER)
    public void removeRoles(final String username, final String... rolePaths) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(rolePaths);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Role> roles = roleRepository.findByPathIn(Arrays.asList(rolePaths));
        roles.stream().filter(role -> ArrayUtils.contains(rolePaths, role.getPath()))
                .forEach(role -> user.getRoles().remove(role));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_ROLES_FROM_USER)
    public void removeRoles(final String username, final Long... roleIds) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(roleIds);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        final List<Role> roles = roleRepository.findAll(Arrays.asList(roleIds));
        roles.stream().filter(role -> ArrayUtils.contains(roleIds, role.getId()))
                .forEach(role -> user.getRoles().remove(role));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasUser(final String username) {
        return userRepository.exists(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUser(final Long id) throws UserNotFoundException {
        Assert.notNull(id);

        User result = userRepository.findOne(id);
        if (result == null) {
            throw new UserNotFoundException(Long.toString(id));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUser(final String username) throws UserNotFoundException {
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
    public Page<User> findUsers(final String usernameFilter, final Pageable pageable) {
        return userRepository.findByUsernameLikeIgnoreCase(DBUtils.wildcard(usernameFilter), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Group> findUserGroups(final String username) {
        return groupRepository.findByUsersUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findUserRoles(final String username) {
        return roleRepository.findByUsersUsername(username);
    }

    /**
     * Gets the default password from configuration.
     *
     * @param password the password to set
     * @return the encoded password
     */
    private String getPasswordDefault(final String password) {
        String result = password;
        if (StringUtils.isBlank(result)) {
            // encrypt the password
            result = passwordEncoder.encode(properties.getUser().getPasswordDefault());
        }

        return result;
    }
}
