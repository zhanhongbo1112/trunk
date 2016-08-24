package com.yqboots.project.fss.core.repository;

import com.yqboots.project.fss.autoconfigure.FssProperties;
import com.yqboots.project.fss.core.FileItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-08-24.
 */
public class FileItemRepositoryImpl implements FileItemRepository {
    private final FssProperties properties;

    private final Path root;

    public FileItemRepositoryImpl(final FssProperties properties) {
        this.properties = properties;
        this.root = properties.getRoot();
    }

    @Override
    public FileItem findOne(final String path) {
        return null;
    }

    @Override
    public Page<FileItem> findByPath(final String path, final Pageable pageable) {
        List<FileItem> content = new ArrayList<>();
        FileItem item = new FileItem();
        item.setName("File Name");
        item.setPath("/test/File Name.pdf");
        item.setLength(200);
        item.setLastModifiedDate(new Date(System.currentTimeMillis()));
        content.add(item);
        return new PageImpl<>(content);
    }

    @Override
    public void save(final FileItem item) {

    }

    @Override
    public void delete(final String path) {

    }
}
