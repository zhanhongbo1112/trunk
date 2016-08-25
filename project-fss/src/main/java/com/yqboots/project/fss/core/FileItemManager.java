package com.yqboots.project.fss.core;

import com.yqboots.project.fss.core.FileItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-08-24.
 */
public interface FileItemManager {
    List<String> getAvailableDirectories();

    Page<FileItem> findByPath(String path, Pageable pageable) throws IOException;

    void delete(String path) throws IOException;

    Path getFullPath(String relativePath);
}
