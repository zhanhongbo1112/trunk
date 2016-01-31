package com.yqsoftwares.security.core;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.audit.SecurityAudit;
import com.yqsoftwares.security.core.audit.annotation.Auditable;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    @Value("${yq.security.user.disabled-when-removing}")
    private boolean disabledWhenRemoving = false;

    @Value("${yq.security.user.password.default}")
    private String defaultPassword;

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
            password = defaultPassword;
        }
        // encrypt the password
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        userRepository.save(user);
    }

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

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_REMOVE_USER)
    public void removeUser(String username) throws UserNotFoundException {
        Assert.hasText(username);

        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (disabledWhenRemoving) {
            user.setEnabled(false);
            userRepository.save(user);
        } else {
            userRepository.delete(user);
        }
    }

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

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_ADD_USER_WITH_GROUPS_AND_ROLES)
    public void addUser(User user, Collection<String> groupPaths, Collection<String> rolePaths) throws UserExistsException {
        Assert.isTrue(user.isNew());
        Assert.hasText(user.getUsername());
        Assert.notNull(groupPaths);
        Assert.notNull(rolePaths);

        if (userRepository.exists(user.getUsername())) {
            throw new UserExistsException(user.getUsername());
        }

        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            // TODO: put the default password to configuration properties
            password = "password";
        }
        // encrypt the password
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        User createdUser = userRepository.saveAndFlush(user);
        if (!groupPaths.isEmpty()) {
            List<Group> groups = groupRepository.findByPathIn(groupPaths);
            if (!groups.isEmpty()) {
                createdUser.setGroups(new HashSet<>(groups));
            }
        }

        if (!rolePaths.isEmpty()) {
            List<Role> roles = roleRepository.findByPathIn(rolePaths);
            if (!roles.isEmpty()) {
                createdUser.setRoles(new HashSet<>(roles));
            }
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    @Auditable(code = SecurityAudit.CODE_UPDATE_USER_WITH_GROUPS_AND_ROLES)
    public void updateUser(User user, Collection<String> groupPaths, Collection<String> rolePaths) throws UserNotFoundException {
        Assert.isTrue(!user.isNew());
        Assert.hasText(user.getUsername());
        Assert.notNull(groupPaths);
        Assert.notNull(rolePaths);

        User entity = userRepository.findByUsername(user.getUsername());
        if (entity == null) {
            throw new UserNotFoundException(user.getUsername());
        }

        entity.getGroups().clear();
        entity.getRoles().clear();
        if (!groupPaths.isEmpty()) {
            final List<Group> groups = groupRepository.findByPathIn(groupPaths);
            if (!groups.isEmpty()) {
                entity.setGroups(new HashSet<>(groups));
            }
        }

        if (!rolePaths.isEmpty()) {
            final List<Role> roles = roleRepository.findByPathIn(rolePaths);
            if (!roles.isEmpty()) {
                entity.setRoles(new HashSet<>(roles));
            }
        }

        userRepository.save(entity);
    }

    @Override
    public boolean hasUser(String username) {
        return userRepository.exists(username);
    }

    @Override
    public User findUser(String username) throws UserNotFoundException {
        Assert.hasText(username);

        User result = userRepository.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException(username);
        }

        return result;
    }

    @Override
    public Page<User> findUsers(String usernameFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(usernameFilter);
        return userRepository.findByUsernameLikeIgnoreCase(filter, pageable);
    }

    @Override
    public Page<Group> findAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
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

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
