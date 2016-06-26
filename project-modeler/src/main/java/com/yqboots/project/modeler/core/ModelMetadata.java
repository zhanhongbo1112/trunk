package com.yqboots.project.modeler.core;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-09.
 */
@SuppressWarnings("serial")
public class ModelMetadata implements Serializable {
    @NotEmpty
    private String name;

    private String description;

    @NotEmpty
    private String groupId;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }
}
