package com.yqboots.project.dict.web.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * TODO: describe the class.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class FileUploadForm {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }
}
