package com.yqboots.project.fss.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-25.
 */
public class FileUploadForm implements Serializable {
    @NotBlank
    private String path;

    @NotNull
    private MultipartFile file;

    private boolean overrideExisting;

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
