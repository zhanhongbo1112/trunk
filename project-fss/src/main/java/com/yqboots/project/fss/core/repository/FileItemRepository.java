package com.yqboots.project.fss.core.repository;

import com.yqboots.project.fss.core.FileItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Created by Administrator on 2016-08-24.
 */
public interface FileItemRepository {
    FileItem findOne(String path) throws IOException;

    Page<FileItem> findByPath(String path, Pageable pageable) throws IOException;

    void save(FileItem item) throws IOException;

    void delete(String path) throws IOException;
}
