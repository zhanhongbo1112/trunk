package com.yqboots.project.menu.security.access;

/**
 * Permission constants for {@link com.yqboots.project.menu.core.MenuItem}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public abstract class MenuItemPermissions {
    /**
     * READ permission expression.
     */
    public static final String READ = "hasPermission('/project/menu', 'com.yqboots.project.menu.core.MenuItem', 'READ')";

    /**
     * WRITE permission expression.
     */
    public static final String WRITE = "hasPermission('/project/menu', 'com.yqboots.project.menu.core.MenuItem', 'WRITE')";

    /**
     * CREATE permission expression.
     */
    public static final String CREATE = "hasPermission('/project/menu', 'com.yqboots.project.menu.core.MenuItem', 'CREATE')";

    /**
     * DELETE permission expression.
     */
    public static final String DELETE = "hasPermission('/project/menu', 'com.yqboots.project.menu.core.MenuItem', 'DELETE')";

    /**
     * ADMINISTRATION permission expression.
     */
    public static final String ADMINISTRATION = "hasPermission('/project/menu', 'com.yqboots.project.menu.core.MenuItem', 'ADMINISTRATION')";
}
