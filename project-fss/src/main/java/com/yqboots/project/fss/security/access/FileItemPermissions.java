package com.yqboots.project.fss.security.access;

/**
 * Permission constants for {@link com.yqboots.project.fss.core.FileItem}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public abstract class FileItemPermissions {
    /**
     * READ permission expression.
     */
    public static final String READ = "hasPermission('/project/fss', 'com.yqboots.project.menu.core.MenuItem', 'READ')";

    /**
     * WRITE permission expression.
     */
    public static final String WRITE = "hasPermission('/project/fss', 'com.yqboots.project.menu.core.MenuItem', 'WRITE')";

    /**
     * CREATE permission expression.
     */
    public static final String CREATE = "hasPermission('/project/fss', 'com.yqboots.project.menu.core.MenuItem', 'CREATE')";

    /**
     * DELETE permission expression.
     */
    public static final String DELETE = "hasPermission('/project/fss', 'com.yqboots.project.menu.core.MenuItem', 'DELETE')";

    /**
     * ADMINISTRATION permission expression.
     */
    public static final String ADMINISTRATION = "hasPermission('/project/fss', 'com.yqboots.project.menu.core.MenuItem', 'ADMINISTRATION')";
}
