package com.yqsoftwares.security.audit.repository;

import com.yqsoftwares.security.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2015-12-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class AuditRepositoryTest {
    @Test
    public void testSave() {
        Assert.fail("not yet implemented");
    }
}