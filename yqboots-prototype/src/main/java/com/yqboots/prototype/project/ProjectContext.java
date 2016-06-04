package com.yqboots.prototype.project;

import com.yqboots.prototype.project.core.ProjectMetadata;
import com.yqboots.prototype.project.core.theme.Theme;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-04.
 */
@SuppressWarnings("serial")
public class ProjectContext implements Serializable {
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
