package com.yqboots.project.menu.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016-06-28.
 */
@Entity
@Table(name = "PRJ_MENUITEM")
public class MenuItem extends AbstractPersistable<Long> {
    private String name;

    private String url;

    private String menu;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(final String menu) {
        this.menu = menu;
    }
}
