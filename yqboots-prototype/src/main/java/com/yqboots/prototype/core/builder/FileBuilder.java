package com.yqboots.prototype.core.builder;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-06-08.
 */
public interface FileBuilder {
    String getTemplate();

    Path getFile(Path root) throws IOException;
}
