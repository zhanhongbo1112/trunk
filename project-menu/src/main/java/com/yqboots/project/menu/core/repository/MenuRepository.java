package com.yqboots.project.menu.core.repository;

import com.yqboots.project.menu.core.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
public class MenuRepository {
    private List<Menu> menus = new ArrayList<>();

    public MenuRepository(final List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> findAll() {
        return menus;
    }
}
