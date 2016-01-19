package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.*;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
    public void addGroup(Group group) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());
        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        groupRepository.save(group);
    }

    @Override
    public void addUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {

            for (User user : users) {
                group.getUsers().add(user);
                user.getGroups().add(group);
            }

            groupRepository.save(group);
        }
    }

    @Override
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
    public void updateGroup(Group group) throws GroupNotFoundException {
        Assert.isTrue(!group.isNew());
        Assert.hasText(group.getPath());

        Group entity = groupRepository.findByPath(group.getPath());
        if (entity == null) {
            throw new GroupNotFoundException(group.getPath());
        }

        BeanUtils.copyProperties(group, entity, new String[]{"id", "path"});

        groupRepository.save(entity);
    }

    @Override
    public void updateUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group entity = groupRepository.findByPath(path);
        if (entity == null) {
            throw new GroupNotFoundException(path);
        }

        entity.getUsers().clear();
        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {
            entity.setUsers(new HashSet<>(users));
            for (User user : users) {
                user.getGroups().remove(entity);
            }
        }

        groupRepository.save(entity);
    }

    @Override
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
    public void removeGroup(String path) throws GroupNotFoundException {
        Assert.hasText(path);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        groupRepository.delete(group);
    }

    @Override
    public void removeGroups(String... paths) {
        Assert.notEmpty(paths);

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(paths));
        if (!groups.isEmpty()) {
            groupRepository.delete(groups);
        }
    }

    @Override
    public void removeUsers(String path, String... usernames) throws GroupNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Group group = groupRepository.findByPath(path);
        if (group == null) {
            throw new GroupNotFoundException(path);
        }

        final List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {
            group.getUsers().removeAll(users);
            for (User user : users) {
                user.getGroups().remove(group);
            }

            groupRepository.save(group);
        }
    }

    @Override
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
    public void addGroup(Group group, Collection<String> usernames, Collection<String> rolePaths) throws GroupExistsException {
        Assert.isTrue(group.isNew());
        Assert.hasText(group.getPath());
        Assert.notNull(usernames);
        Assert.notNull(rolePaths);

        if (groupRepository.exists(group.getPath())) {
            throw new GroupExistsException(group.getPath());
        }

        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            if (!users.isEmpty()) {
                group.setUsers(new HashSet<>(users));
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

        entity.getUsers().clear();
        entity.getRoles().clear();
        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            if (!users.isEmpty()) {
                group.setUsers(new HashSet<>(users));
            }
        }

        if (!rolePaths.isEmpty()) {
            List<Role> roles = roleRepository.findByPathIn(rolePaths);
            if (!roles.isEmpty()) {
                group.setRoles(new HashSet<>(roles));
            }
        }

        groupRepository.save(entity);
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
