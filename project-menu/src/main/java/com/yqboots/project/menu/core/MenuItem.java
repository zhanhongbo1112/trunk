package com.yqboots.project.menu.core;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-28.
 */
public class MenuItem implements Serializable {
    private String id;
    private String name;
    private String group;

    public MenuItem() {
        super();
    }

    public MenuItem(final String id, final String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public MenuItem(final String id, final String name, final String group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }
}
