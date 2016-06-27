package com.yqboots.project.menu.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
public class Menu implements Serializable {
    private String id;
    private List<MenuItem> items = new ArrayList<>();

    public Menu() {
        super();
    }

    public Menu(final String id, final List<MenuItem> items) {
        super();
        this.id = id;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(final List<MenuItem> items) {
        this.items = items;
    }
}
