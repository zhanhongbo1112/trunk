package com.yqboots.project.menu.core;

import com.yqboots.project.menu.core.MenuItem;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016-08-12.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuItems implements Serializable {
    @XmlElement(name = "menuItem", required = true)
    private List<MenuItem> menuItems;

    protected MenuItems() {
        super();
    }

    public MenuItems(final List<MenuItem> menuItems) {
        super();
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(final List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
