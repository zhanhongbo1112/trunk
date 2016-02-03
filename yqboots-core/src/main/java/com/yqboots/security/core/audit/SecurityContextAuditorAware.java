package com.yqboots.security.core.audit;

import com.yqboots.security.util.SecurityUtils;
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
