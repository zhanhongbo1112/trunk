package com.yqboots.prototype.core.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-06-11.
 */
public class FileBuilderImpl implements FileBuilder {
    private final String template;

    private final String path;

    public FileBuilderImpl(final String template, final String path) {
        this.template = template;
        this.path = path;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public Path getFile(final Path root) throws IOException {
        Path result = Paths.get(root.toAbsolutePath() + path);
        if (Files.exists(result)) {
            result.toFile().createNewFile();
        } else {
            Files.createFile(result);
        }

        return result;
    }
}
