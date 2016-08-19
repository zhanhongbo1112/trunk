package com.yqboots.project.menu.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
public interface MenuItemManager {
    List<MenuItem> getMenuItems();

    MenuItem getMenuItem(String name);

    void imports(InputStream inputStream) throws IOException;

    Path exports() throws IOException;
}
