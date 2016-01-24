package com.yqsoftwares.security.audit;

import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015-12-15.
 */
@Component
public class SecurityContextAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return SecurityUtils.getCurrentUser().getUsername();
    }
}
