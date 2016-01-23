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
public class RoleManagerImpl implements RoleManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addRole(Role role) throws RoleExistsException {
        Assert.isTrue(role.isNew());
        Assert.hasText(role.getPath());
        if (roleRepository.exists(role.getPath())) {
            throw new RoleExistsException(role.getPath());
        }

        roleRepository.save(role);
    }

    @Override
    public void addUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {
            for (User user : users) {
                role.getUsers().add(user);
                user.getRoles().add(role);
            }

            roleRepository.save(role);
        }
    }

    @Override
    public void addGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(path));
        if (!groups.isEmpty()) {
            role.getGroups().addAll(groups);
            roleRepository.save(role);
        }
    }

    @Override
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

    @Override
    public void updateUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role entity = roleRepository.findByPath(path);
        if (entity == null) {
            throw new RoleNotFoundException(path);
        }

        entity.getUsers().clear();
        List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {
            entity.setUsers(new HashSet<>(users));
            for (User user : users) {
                user.getRoles().remove(entity);
            }
        }

        roleRepository.save(entity);
    }

    @Override
    public void updateGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role entity = roleRepository.findByPath(path);
        if (entity == null) {
            throw new RoleNotFoundException(path);
        }

        entity.getUsers().clear();
        List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        if (!groups.isEmpty()) {
            entity.setGroups(new HashSet<>(groups));
            for (Group group : groups) {
                group.getRoles().remove(entity);
            }
        }

        roleRepository.save(entity);
    }

    @Override
    public void removeRole(String path) throws RoleNotFoundException {
        Assert.hasText(path);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        roleRepository.delete(role);
    }

    @Override
    public void removeUsers(String path, String... usernames) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(usernames);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        final List<User> users = userRepository.findByUsernameIn(Arrays.asList(usernames));
        if (!users.isEmpty()) {
            role.getUsers().removeAll(users);
            for (User user : users) {
                user.getGroups().remove(role);
            }

            roleRepository.save(role);
        }
    }

    @Override
    public void removeGroups(String path, String... groupPaths) throws RoleNotFoundException {
        Assert.hasText(path);
        Assert.notEmpty(groupPaths);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        final List<Group> groups = groupRepository.findByPathIn(Arrays.asList(groupPaths));
        if (!groups.isEmpty()) {
            role.getGroups().removeAll(groups);
            for (Group user : groups) {
                user.getRoles().remove(role);
            }

            roleRepository.save(role);
        }
    }

    @Override
    public void addRole(Role role, Collection<String> usernames, Collection<String> groupPaths) throws RoleExistsException {
        Assert.isTrue(role.isNew());
        Assert.hasText(role.getPath());
        Assert.notNull(usernames);
        Assert.notNull(groupPaths);

        if (roleRepository.exists(role.getPath())) {
            throw new RoleExistsException(role.getPath());
        }

        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            if (!users.isEmpty()) {
                role.setUsers(new HashSet<>(users));
            }
        }

        if (!groupPaths.isEmpty()) {
            List<Group> groups = groupRepository.findByPathIn(groupPaths);
            if (!groups.isEmpty()) {
                role.setGroups(new HashSet<>(groups));
            }
        }

        roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role, Collection<String> usernames, Collection<String> groupPaths) throws GroupNotFoundException {
        Assert.isTrue(!role.isNew());
        Assert.hasText(role.getPath());
        Assert.notNull(usernames);
        Assert.notNull(groupPaths);

        Role entity = roleRepository.findByPath(role.getPath());
        if (entity == null) {
            throw new GroupNotFoundException(role.getPath());
        }

        entity.setAlias(role.getAlias());
        entity.setDescription(role.getDescription());

        entity.getUsers().clear();
        entity.getGroups().clear();
        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            if (!users.isEmpty()) {
                role.setUsers(new HashSet<>(users));
            }
        }

        if (!groupPaths.isEmpty()) {
            List<Group> groups = groupRepository.findByPathIn(groupPaths);
            if (!groups.isEmpty()) {
                role.setGroups(new HashSet<>(groups));
            }
        }

        roleRepository.save(entity);
    }

    @Override
    public boolean hasRole(String path) {
        return roleRepository.exists(path);
    }

    @Override
    public Role findRole(String path) throws RoleNotFoundException {
        Assert.hasText(path);

        Role role = roleRepository.findByPath(path);
        if (role == null) {
            throw new RoleNotFoundException(path);
        }

        return role;
    }

    @Override
    public Page<Role> findRoles(String pathFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(pathFilter);
        return roleRepository.findByPathLikeIgnoreCase(filter, pageable);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<Group> findAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }
}
