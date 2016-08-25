package com.yqboots.project.fss.core.support;

import com.yqboots.project.fss.core.FileItem;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2016-08-26.
 */
public class FileItemConsumer implements Consumer<Path> {
    private final Path root;

    private List<FileItem> items = new ArrayList<>();

    public FileItemConsumer(final Path root, final List<FileItem> items) {
        this.root = root;
        this.items = items;
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param path the input argument
     */
    @Override
    public void accept(final Path path) {
        final File file = path.toFile();
        FileItem item = new FileItem();
        item.setName(file.getName());
        item.setPath(StringUtils.substringAfter(file.getPath(), root.toString()));
        item.setLength(file.length());
        item.setLastModifiedDate(LocalDateTime.ofInstant(new Date(file.lastModified()).toInstant(),
                ZoneId.systemDefault()));
        items.add(item);
    }
}
