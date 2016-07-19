package com.yqboots.project.menu.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Created by Administrator on 2016-06-28.
 */
public class MenuItem extends AbstractPersistable<Long> {
    private String name;
    private String group;
    private String subGroup;

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

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(final String subGroup) {
        this.subGroup = subGroup;
    }
}
