package com.yqboots.menu.service;

import com.yqboots.menu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class MenuItemServiceTest {
    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void testGetMenus() throws Exception {
        assertFalse(menuItemService.getMenuItems().isEmpty());
    }
}