package com.yqboots.security.util;

import com.yqboots.security.core.Role;
import com.yqboots.security.core.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2015-12-14.
 */
public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static User getCurrentUser() {
        User result = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User _user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                Set<Role> roles = _user.getAuthorities().stream().map(auth -> new Role(auth.getAuthority())).collect(Collectors.toSet());
                result = new User(_user.getUsername(), _user.getPassword(), roles);
            } else {
                result = (User) authentication.getPrincipal();
            }
        }

        return result;
    }
}
