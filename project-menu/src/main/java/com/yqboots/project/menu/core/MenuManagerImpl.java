package com.yqboots.project.menu.core;

import com.yqboots.project.menu.core.repository.MenuRepository;

import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
public class MenuManagerImpl implements MenuManager {
    private MenuRepository menuRepository;

    public MenuManagerImpl(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getMenus() {
        return menuRepository.findAll();
    }
}
