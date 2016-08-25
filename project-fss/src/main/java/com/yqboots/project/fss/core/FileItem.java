package com.yqboots.project.fss.core;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2016-08-24.
 */
public class FileItem implements Serializable {
    private String name;

    private String path;

    private LocalDateTime lastModifiedDate;

    private long length;

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

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getLength() {
        return length;
    }

    public void setLength(final long length) {
        this.length = length;
    }
}
