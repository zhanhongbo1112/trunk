package com.yqboots.project.fss.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016-08-24.
 */
public class FileItem implements Serializable {
    private String name;

    private String path;

    private Date lastModifiedDate;

    private int length;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }
}
