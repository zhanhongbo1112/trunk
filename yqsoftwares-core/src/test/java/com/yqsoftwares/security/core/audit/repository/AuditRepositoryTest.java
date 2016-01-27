package com.yqsoftwares.security.core.audit.repository;

import com.yqsoftwares.security.Application;
import com.yqsoftwares.security.core.audit.Audit;
import com.yqsoftwares.security.core.audit.SecurityAudit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2015-12-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Sql
public class AuditRepositoryTest {
    @Autowired
    private AuditRepository auditRepository;

    @Test
    @WithUserDetails("admin")
    public void testSave() {
        SecurityAudit audit = new SecurityAudit();
        audit.setCode(0);
        auditRepository.save(audit);

        List<Audit> audits = auditRepository.findAll();

        Assert.assertTrue(!audits.isEmpty());
    }
}