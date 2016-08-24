package com.yqboots.project.fss.core.convert;

import org.springframework.core.convert.converter.Converter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-08-15.
 */
public class StringToPathConverter implements Converter<String, Path> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Path convert(final String source) {
        return Paths.get(source);
    }
}
