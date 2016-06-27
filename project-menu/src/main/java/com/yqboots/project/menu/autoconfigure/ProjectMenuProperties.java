package com.yqboots.project.menu.autoconfigure;

import com.yqboots.project.menu.core.Menu;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
@ConfigurationProperties(prefix = "yqboots.project.menu")
public class ProjectMenuProperties {
    private List<Menu> menus = new ArrayList<>();

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(final List<Menu> menus) {
        this.menus = menus;
    }
}
