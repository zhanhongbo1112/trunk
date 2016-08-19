package com.yqboots.project.dict.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
public interface DataDictManager {
    List<DataDict> getDataDicts(String name);

    String getText(String name, String value);

    String getText(String name, String value, boolean valueIncluded);

    void imports(InputStream inputStream) throws IOException;

    Path exports() throws IOException;
}
