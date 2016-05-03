package com.yqboots.security.core;

import com.yqboots.security.core.audit.SecurityAudit;
import com.yqboots.security.core.audit.annotation.Auditable;
import com.yqboots.security.core.repository.GroupRepository;
import com.yqboots.security.core.repository.RoleRepository;
import com.yqboots.security.core.repository.UserRepository;
import com.yqboots.util.DBUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
 */
@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('/SUPERVISOR')")
public class GroupManagerImpl implements GroupManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_GROUP)
    public void addGroup(Group group) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());
        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        groupRepository.save(group);
    }

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

    @Override
    public boolean hasGroup(String path) {
        return groupRepository.exists(path);
    }

    @Override
    public Group findGroup(String path) throws GroupNotFoundException {
        Assert.hasText(path);

        Group result = groupRepository.findByPath(path);
        if (result == null) {
            throw new GroupNotFoundException(path);
        }

        return result;
    }

    @Override
    public Page<Group> findGroups(String pathFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(pathFilter);
        return groupRepository.findByPathLikeIgnoreCase(filter, pageable);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findGroupUsers(String path) {
        return userRepository.findByGroupsPath(path);
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<Role> findGroupRoles(String path) {
        return roleRepository.findByGroupsPath(path);
    }
}
