package com.yqboots.security.core.audit.repository;

import com.yqboots.security.Application;
import com.yqboots.security.core.audit.SecurityAudit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WithUserDetails("user")
public class SecurityAuditRepositoryTest {
    @Autowired
    private SecurityAuditRepository securityAuditRepository;

    @Test
    public void testSave() {
        SecurityAudit audit = new SecurityAudit(0);
        audit.setTarget("object id");
        securityAuditRepository.save(audit);

        List<SecurityAudit> audits = securityAuditRepository.findAll();

        Assert.assertTrue(!audits.isEmpty());
    }
}