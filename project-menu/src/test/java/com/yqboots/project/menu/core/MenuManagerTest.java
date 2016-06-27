package com.yqboots.project.menu.core;

import com.yqboots.project.menu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class MenuManagerTest {
    @Autowired
    private MenuManager menuManager;

    @Test
    public void testGetMenus() throws Exception {
        assertFalse(menuManager.getMenus().isEmpty());
    }
}