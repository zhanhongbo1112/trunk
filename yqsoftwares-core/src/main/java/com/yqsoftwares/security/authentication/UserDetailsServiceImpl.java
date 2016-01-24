package com.yqsoftwares.security.authentication;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2015-12-16.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired(required = false)
    private boolean enableAuthorities = true;

    @Autowired(required = false)
    private boolean enableGroups = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Assert.hasLength(username, "username is required");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.info("Query returned no results for user {}", username);
            throw new UsernameNotFoundException("DefaultUserDetailsService.notFound");
        }

        Set<Role> dbAuthsSet = new HashSet<>();
        if (enableAuthorities) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }

        if (enableGroups) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }

        addCustomAuthorities(user.getUsername(), dbAuthsSet);

        if (dbAuthsSet.size() == 0) {
            LOGGER.info("User {} has no authorities and will be treated as 'not found'", username);
            throw new UsernameNotFoundException("DefaultUserDetailsService.noAuthority");
        }

        return createUserDetails(user, dbAuthsSet);
    }

    protected UserDetails createUserDetails(User userFromQuery, Collection<Role> combinedAuthorities) {
        String returnUsername = userFromQuery.getUsername();

        return new User(userFromQuery.getId(), returnUsername, userFromQuery.getPassword(), userFromQuery.isEnabled(),
                new HashSet<>(combinedAuthorities));
    }

    protected List<Role> loadUserAuthorities(String username) {
        return roleRepository.findByUsersUsername(username);
    }

    protected List<Role> loadGroupAuthorities(String username) {
        List<Role> results = new ArrayList<>();

        final List<Group> groups = groupRepository.findByUsersUsername(username);

        if (!groups.isEmpty()) {
            Set<String> groupPaths = groups.stream().map(Group::getPath).collect(Collectors.toSet());

            results = roleRepository.findByGroupsPathIn(groupPaths);
        }

        return results;
    }

    protected void addCustomAuthorities(String username, Collection<Role> authorities) {
    }
}
