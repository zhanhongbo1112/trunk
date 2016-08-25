package com.yqboots.project.fss.core;

import com.yqboots.project.fss.autoconfigure.FssProperties;
import com.yqboots.project.fss.core.support.FileItemConsumer;
import com.yqboots.project.fss.core.support.FileTypeFilterPredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-08-24.
 */
public class FileItemManagerImpl implements FileItemManager {
    private final FssProperties properties;

    private final Path root;

    public FileItemManagerImpl(final FssProperties properties) {
        this.properties = properties;
        this.root = properties.getRoot();
    }

    @Override
    public List<String> getAvailableDirectories() {
        List<String> results = new ArrayList<>();

        List<Path> paths = this.properties.getDirectories();

        results.addAll(paths.stream().map(path -> StringUtils.substringAfter(path.toString(),
                root.toString())).collect(Collectors.toList()));

        return results;
    }

    @Override
    public Page<FileItem> findByPath(final String path, final Pageable pageable) throws IOException {
        final List<FileItem> items = new ArrayList<>();

        final Path dir = Paths.get(this.root + path);
        if (!Files.exists(dir)) {
            return new PageImpl<>(items, pageable, items.size());
        }

        Files.list(dir).filter(new FileTypeFilterPredicate(properties.getFileTypes()))
                .forEach(new FileItemConsumer(root, items));

        return new PageImpl<>(items, pageable, items.size());
    }

    @Override
    public void delete(final String path) throws IOException {
        Path file = Paths.get(this.root + path);
        Files.deleteIfExists(file);
    }

    @Override
    public Path getFullPath(final String relativePath) {
        return Paths.get(this.root + relativePath);
    }
}
