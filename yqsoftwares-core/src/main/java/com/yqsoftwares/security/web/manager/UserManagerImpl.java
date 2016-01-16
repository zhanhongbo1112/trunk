package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.commons.db.util.DBUtils;
import com.yqsoftwares.security.core.*;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
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

    @Override
    @Transactional
    public void addUser(User entity, Collection<String> groups, Collection<String> roles) {
        Assert.isTrue(entity.isNew());
        Assert.hasText(entity.getUsername());
        Assert.notNull(groups);
        Assert.notNull(roles);
        if (userRepository.exists(entity.getUsername())) {
            throw new UserExistsException(entity.getUsername());
        }

        String password = entity.getPassword();
        if (StringUtils.isEmpty(password)) {
            // TODO: put the default password to configuration properties
            password = "password";
        }
        // encrypt the password
        password = passwordEncoder.encode(password);
        entity.setPassword(password);

        List<Group> inGroups = groupRepository.findByPathIn(groups);
        if (!inGroups.isEmpty()) {
            entity.setGroups(new HashSet<>(inGroups));
        }

        List<Role> inRoles = roleRepository.findByPathIn(roles);
        if (!inRoles.isEmpty()) {
            entity.setRoles(new HashSet<>(inRoles));
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateUser(User entity, Collection<String> groups, Collection<String> roles) {
        Assert.isTrue(!entity.isNew());
        Assert.hasText(entity.getUsername());
        Assert.notNull(groups);
        Assert.notNull(roles);

        if (!userRepository.exists(entity.getUsername())) {
            throw new UserNotFoundException(entity.getUsername());
        }

        User user = userRepository.findByUsername(entity.getUsername());
        user.getGroups().clear();
        user.getRoles().clear();
        userRepository.save(user);

        final List<Group> inGroups = groupRepository.findByPathIn(groups);
        if (!inGroups.isEmpty()) {
            user.setGroups(new HashSet<>(inGroups));
        }

        final List<Role> inRoles = roleRepository.findByPathIn(roles);
        if (!inRoles.isEmpty()) {
            user.setRoles(new HashSet<>(inRoles));
        }

        userRepository.save(entity);
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
    public void updateState(String username, boolean enabled) {
        Assert.hasText(username);
        if (!userRepository.exists(username)) {
            throw new UserNotFoundException(username);
        }

        final User user = userRepository.findByUsername(username);
//        if (user.isEnabled() == enabled) {
//            // if in the same state, no update
//            return;
//        }

        user.setEnabled(enabled);
        userRepository.save(user);
    }
}
