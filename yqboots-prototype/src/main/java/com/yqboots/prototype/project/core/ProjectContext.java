package com.yqboots.prototype.project.core;

import com.yqboots.prototype.project.core.theme.Theme;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-04.
 */
@SuppressWarnings("serial")
public class ProjectContext implements Serializable {
    public static final String KEY = "context";

    private ProjectMetadata metadata;

    private Theme theme;

    public ProjectMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ProjectMetadata metadata) {
        this.metadata = metadata;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
