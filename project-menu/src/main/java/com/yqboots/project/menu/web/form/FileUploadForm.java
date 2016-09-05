package com.yqboots.project.menu.web.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * File Upload Form.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class FileUploadForm {
    /**
     * The uploading file.
     */
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }
}
