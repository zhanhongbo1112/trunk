package com.yqboots.project.fss.core.support;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2016-08-26.
 */
public class FileTypeFilterPredicate implements Predicate<Path> {
    private String[] acceptedFileTypes = new String[]{};

    public FileTypeFilterPredicate(final String[] acceptedFileTypes) {
        this.acceptedFileTypes = acceptedFileTypes;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param path the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(final Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }

        boolean result = false;
        for (final String fileType : acceptedFileTypes) {
            if (StringUtils.endsWith(path.getFileName().toString(), fileType)) {
                result = true;
                break;
            }
        }

        return result;
    }
}
