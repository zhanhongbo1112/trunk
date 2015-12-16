package com.yqsoftwares.security.authentication;

import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashSet;

/**
 * Created by Administrator on 2015-12-16.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasLength(username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username NOT FOUND");
        }

        // TODO: LOAD USER GROUPS
        // TODO: LOAD USER ROLES (INCLUDING USER GROUPS' ROLES)

        return new User(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled(), new HashSet<>());
    }
}
