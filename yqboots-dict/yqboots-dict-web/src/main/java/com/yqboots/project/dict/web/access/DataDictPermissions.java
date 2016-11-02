package com.yqboots.project.dict.web.access;

/**
 * Permission constants for {@link com.yqboots.project.dict.core.DataDict}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public abstract class DataDictPermissions {
    /**
     * READ permission expression.
     */
    public static final String READ = "hasPermission('/project/dict', 'com.yqboots.project.menu.core.MenuItem', 'READ')";

    /**
     * WRITE permission expression.
     */
    public static final String WRITE = "hasPermission('/project/dict', 'com.yqboots.project.menu.core.MenuItem', 'WRITE')";

    /**
     * CREATE permission expression.
     */
    public static final String CREATE = "hasPermission('/project/dict', 'com.yqboots.project.menu.core.MenuItem', 'CREATE')";

    /**
     * DELETE permission expression.
     */
    public static final String DELETE = "hasPermission('/project/dict', 'com.yqboots.project.menu.core.MenuItem', 'DELETE')";

    /**
     * ADMINISTRATION permission expression.
     */
    public static final String ADMINISTRATION = "hasPermission('/project/dict', 'com.yqboots.project.menu.core.MenuItem', 'ADMINISTRATION')";
}
