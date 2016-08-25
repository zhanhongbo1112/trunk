package com.yqboots.project.fss.web.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-25.
 */
public class FileUploadForm implements Serializable {
    private String name;

    private String path;

    private MultipartFile file;

    private boolean overrideExisting;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public boolean isOverrideExisting() {
        return overrideExisting;
    }

    public void setOverrideExisting(final boolean overrideExisting) {
        this.overrideExisting = overrideExisting;
    }
}
