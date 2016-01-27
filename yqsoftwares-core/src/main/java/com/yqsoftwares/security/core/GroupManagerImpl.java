package com.yqsoftwares.security.core;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.audit.annotation.Auditable;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
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

    @Override
    @Transactional
    @Auditable(code = 1)
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
    public void removeGroups(String... paths) {
        Assert.notEmpty(paths);

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(paths));
        if (!groups.isEmpty()) {
            for (Group group : groups) {
                List<User> users = userRepository.findByGroupsPath(group.getPath());
                for (User user : users) {
                    user.getGroups().remove(group);
                    userRepository.saveAndFlush(user);
                }

                groupRepository.delete(group);
            }
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public void addGroup(Group group, Collection<String> usernames, Collection<String> rolePaths) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());
        Assert.notNull(usernames);
        Assert.notNull(rolePaths);

        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        Group createdGroup = groupRepository.saveAndFlush(group);

        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            for (User user : users) {
                user.getGroups().add(createdGroup);
                userRepository.save(user);
            }
        }

        if (!rolePaths.isEmpty()) {
            List<Role> roles = roleRepository.findByPathIn(rolePaths);
            if (!roles.isEmpty()) {
                group.setRoles(new HashSet<>(roles));
            }
        }

        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void updateGroup(Group group, Collection<String> usernames, Collection<String> rolePaths) throws GroupNotFoundException {
        Assert.isTrue(!group.isNew());
        Assert.hasText(group.getPath());
        Assert.notNull(usernames);
        Assert.notNull(rolePaths);

        Group entity = groupRepository.findByPath(group.getPath());
        if (entity == null) {
            throw new GroupNotFoundException(group.getPath());
        }

        entity.setAlias(group.getAlias());
        entity.setDescription(group.getDescription());

        Group updatedGroup = groupRepository.saveAndFlush(entity);
        for (User user : updatedGroup.getUsers()) {
            user.getGroups().remove(updatedGroup);
            userRepository.saveAndFlush(user);
        }
        updatedGroup.getRoles().clear();
        updatedGroup = groupRepository.saveAndFlush(updatedGroup);

        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            for (User user : users) {
                user.getGroups().add(updatedGroup);
                userRepository.save(user);
            }
        }

        if (!rolePaths.isEmpty()) {
            List<Role> roles = roleRepository.findByPathIn(rolePaths);
            if (!roles.isEmpty()) {
                updatedGroup.setRoles(new HashSet<>(roles));
                groupRepository.save(updatedGroup);
            }
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
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}
