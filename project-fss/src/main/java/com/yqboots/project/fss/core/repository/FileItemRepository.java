package com.yqboots.project.fss.core.repository;

import com.yqboots.project.fss.core.FileItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2016-08-24.
 */
public interface FileItemRepository {
    FileItem findOne(String path);

    Page<FileItem> findByPath(String path, Pageable pageable);

    void save(FileItem item);

    void delete(String path);
}
