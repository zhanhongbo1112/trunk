package com.yqboots.project.fss.core.repository;

import com.yqboots.project.fss.autoconfigure.FssProperties;
import com.yqboots.project.fss.core.FileItem;
import com.yqboots.project.fss.core.support.FileType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    public FileItem findOne(final String path) throws IOException {
        return null;
    }

    @Override
    public Page<FileItem> findByPath(final String path, final Pageable pageable) throws IOException {
        final List<FileItem> content = new ArrayList<>();

        final Path dir = Paths.get(this.root + path);
        if (!Files.exists(dir)) {
            return new PageImpl<>(content, pageable, content.size());
        }

        Files.list(dir).filter(new Predicate<Path>() {
            @Override
            public boolean test(final Path path) {
                return Files.isRegularFile(path) && StringUtils.endsWith(path.getFileName().toString(), FileType.DOT_XML);
            }
        }).forEach(new Consumer<Path>() {
            @Override
            public void accept(final Path path) {
                final File file = path.toFile();
                FileItem item = new FileItem();
                item.setName(file.getName());
                item.setPath(StringUtils.substringAfter(file.getPath(), root.toString()));
                item.setLength(file.length());
                item.setLastModifiedDate(LocalDateTime.ofInstant(new Date(file.lastModified()).toInstant(),
                        ZoneId.systemDefault()));
                content.add(item);
            }
        });

        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public void save(final FileItem item) throws IOException {

    }

    @Override
    public void delete(final String path) throws IOException {

    }
}
