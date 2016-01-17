package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.*;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    public void addGroups(String username, String... groups) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(groups);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);

        List<Group> inGroups = groupRepository.findByPathIn(Arrays.asList(groups));
        if (!inGroups.isEmpty()) {
            entity.getGroups().addAll(inGroups);
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void addRoles(String username, String... roles) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(roles);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);

        List<Role> inRoles = roleRepository.findByPathIn(Arrays.asList(roles));
        if (!inRoles.isEmpty()) {
            entity.getRoles().addAll(inRoles);
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws UserNotFoundException {
        Assert.isTrue(!user.isNew());
        Assert.hasText(user.getUsername());

        if (!userRepository.exists(user.getUsername())) {
            throw new UserNotFoundException(user.getUsername());
        }

        User entity = userRepository.findByUsername(user.getUsername());
        BeanUtils.copyProperties(user, entity, new String[]{"id", "password"});

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateGroups(String username, String... groups) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(groups);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);
        entity.getGroups().clear();
        if (ArrayUtils.isNotEmpty(groups)) {
            final List<Group> inGroups = groupRepository.findByPathIn(Arrays.asList(groups));
            if (!inGroups.isEmpty()) {
                entity.setGroups(new HashSet<>(inGroups));
            }
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateRoles(String username, String... roles) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(roles);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);
        entity.getRoles().clear();
        if (ArrayUtils.isNotEmpty(roles)) {
            final List<Role> inRoles = roleRepository.findByPathIn(Arrays.asList(roles));
            if (!inRoles.isEmpty()) {
                entity.setRoles(new HashSet<>(inRoles));
            }
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void removeUser(String username) throws UserNotFoundException {
        Assert.hasText(username);
        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        final User entity = userRepository.findByUsername(username);
        if (disabledWhenRemoving) {
            entity.setEnabled(false);
            userRepository.save(entity);
        } else {
            userRepository.delete(entity);
        }
    }

    @Override
    @Transactional
    public void removeGroups(String username, String... groups) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(groups);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);
        if (ArrayUtils.isNotEmpty(groups)) {
            final List<Group> inGroups = groupRepository.findByPathIn(Arrays.asList(groups));
            if (!inGroups.isEmpty()) {
                entity.getGroups().removeAll(inGroups);
            }
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void removeRoles(String username, String... roles) throws UserNotFoundException {
        Assert.hasText(username);
        Assert.notNull(roles);

        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        User entity = userRepository.findByUsername(username);
        if (ArrayUtils.isNotEmpty(roles)) {
            final List<Role> inRoles = roleRepository.findByPathIn(Arrays.asList(roles));
            if (!inRoles.isEmpty()) {
                entity.getRoles().removeAll(inRoles);
            }
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void addUser(User user, Collection<String> groups, Collection<String> roles) throws UserExistsException {
        Assert.isTrue(user.isNew());
        Assert.hasText(user.getUsername());
        Assert.notNull(groups);
        Assert.notNull(roles);

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

        List<Group> inGroups = groupRepository.findByPathIn(groups);
        if (!inGroups.isEmpty()) {
            user.setGroups(new HashSet<>(inGroups));
        }

        List<Role> inRoles = roleRepository.findByPathIn(roles);
        if (!inRoles.isEmpty()) {
            user.setRoles(new HashSet<>(inRoles));
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, Collection<String> groups, Collection<String> roles) throws UserNotFoundException {
        Assert.isTrue(!user.isNew());
        Assert.hasText(user.getUsername());
        Assert.notNull(groups);
        Assert.notNull(roles);

        if (!userRepository.exists(user.getUsername())) {
            throw new UserNotFoundException(user.getUsername());
        }

        User entity = userRepository.findByUsername(user.getUsername());
        entity.getGroups().clear();
        entity.getRoles().clear();
        userRepository.save(entity);

        if (CollectionUtils.isNotEmpty(groups)) {
            final List<Group> inGroups = groupRepository.findByPathIn(groups);
            if (!inGroups.isEmpty()) {
                entity.setGroups(new HashSet<>(inGroups));
            }
        }

        if (CollectionUtils.isNotEmpty(roles)) {
            final List<Role> inRoles = roleRepository.findByPathIn(roles);
            if (!inRoles.isEmpty()) {
                entity.setRoles(new HashSet<>(inRoles));
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
        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findUsers(String usernameFilter, Pageable pageable) {
        final String filter = DBUtils.wildcard(usernameFilter);
        return userRepository.findByUsernameLikeIgnoreCase(filter, pageable);
    }

    @Override
    public List<Group> findAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public void updateState(String username, boolean enabled) throws UserNotFoundException {
        Assert.hasText(username);
        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        final User user = userRepository.findByUsername(username);
        user.setEnabled(enabled);
        userRepository.save(user);
    }
}
