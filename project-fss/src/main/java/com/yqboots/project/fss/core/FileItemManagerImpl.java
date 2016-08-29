/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.project.fss.core;

import com.yqboots.project.fss.autoconfigure.FssProperties;
import com.yqboots.project.fss.core.support.FileItemConsumer;
import com.yqboots.project.fss.core.support.FileTypeFilterPredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The service implementation that manages the FileItem..
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class FileItemManagerImpl implements FileItemManager {
    private final FssProperties properties;

    /**
     * The root path.
     */
    private final Path root;

    public FileItemManagerImpl(final FssProperties properties) {
        this.properties = properties;
        this.root = properties.getRoot();
    }

    /**
     * Gets available directories which will be maintained in the UI.
     *
     * @return list of directories
     */
    @Override
    public List<String> getAvailableDirectories() {
        final List<String> results = new ArrayList<>();

        final List<Path> paths = this.properties.getDirectories();
        if (!CollectionUtils.isEmpty(paths)) {
            results.addAll(paths.stream().map(path -> StringUtils.substringAfter(path.toString(),
                    root.toString())).collect(Collectors.toList()));
        }

        return results;
    }

    /**
     * Finds by directory path.
     *
     * @param path     the directory to search
     * @param pageable the paged information
     * @return paged of FileItem
     * @throws IOException if failed
     */
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

    /**
     * Deletes the file with the specified path.
     *
     * @param path the file path, not directory.
     * @throws IOException
     */
    @Override
    public void delete(final String path) throws IOException {
        final Path file = Paths.get(this.root + path);
        // if not a file, ignore directly
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            return;
        }

        Files.deleteIfExists(file);
    }

    /**
     * Gets the full path based on the root directory.
     *
     * @param relativePath the relative path to the root
     * @return the full path
     */
    @Override
    public Path getFullPath(final String relativePath) {
        return Paths.get(this.root + relativePath);
    }
}
