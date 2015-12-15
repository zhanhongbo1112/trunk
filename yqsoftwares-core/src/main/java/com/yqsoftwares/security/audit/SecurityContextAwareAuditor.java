package com.yqsoftwares.security.audit;

import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

/**
 * Created by Administrator on 2015-12-15.
 */
public class SecurityContextAwareAuditor implements AuditorAware<User> {
    @Override
    public User getCurrentAuditor() {
        return SecurityUtils.getCurrentUser();
    }
}
