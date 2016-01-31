package com.yqsoftwares.security.core;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.audit.SecurityAudit;
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

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_ROLE_WITH_USERS_AND_GROUPS)
    public void addRole(Role role, Collection<String> usernames, Collection<String> groupPaths) throws RoleExistsException {
        Assert.isTrue(role.isNew());
        Assert.hasText(role.getPath());
        Assert.notNull(usernames);
        Assert.notNull(groupPaths);

        if (roleRepository.exists(role.getPath())) {
            throw new RoleExistsException(role.getPath());
        }

        Role createdRole = roleRepository.saveAndFlush(role);
        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            for (User user : users) {
                user.getRoles().add(createdRole);
                userRepository.save(user);
            }
        }

        if (!groupPaths.isEmpty()) {
            List<Group> groups = groupRepository.findByPathIn(groupPaths);
            for (Group group : groups) {
                group.getRoles().add(createdRole);
                groupRepository.save(group);
            }
        }
    }

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_ROLE_WITH_USERS_AND_GROUPS)
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

        Role updatedRole = roleRepository.saveAndFlush(entity);
        for (User user : updatedRole.getUsers()) {
            user.getRoles().remove(updatedRole);
            userRepository.saveAndFlush(user);
        }
        for (Group group : updatedRole.getGroups()) {
            group.getRoles().remove(updatedRole);
            groupRepository.saveAndFlush(group);
        }

        if (!usernames.isEmpty()) {
            List<User> users = userRepository.findByUsernameIn(usernames);
            for (User user : users) {
                user.getRoles().add(updatedRole);
                userRepository.save(user);
            }
        }

        if (!groupPaths.isEmpty()) {
            List<Group> groups = groupRepository.findByPathIn(groupPaths);
            for (Group group : groups) {
                group.getRoles().add(updatedRole);
                groupRepository.save(group);
            }
        }
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

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
