package com.yqboots.project.menu.core;

import com.yqboots.project.menu.core.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
@Service
@Transactional(readOnly = true)
public class MenuItemManagerImpl implements MenuItemManager {
    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemManagerImpl(final MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    // TODO: @PostFilter for security
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItem(final String name) {
        return menuItemRepository.findByName(name);
    }
}
