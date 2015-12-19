package com.yqsoftwares.security.util;

import com.yqsoftwares.security.core.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Administrator on 2015-12-14.
 */
public final class SecurityUtils {
    public static User getCurrentUser() {
        User result = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            result = (User) authentication.getPrincipal();
        }

        return result;
    }

    private SecurityUtils() {
    }
}
