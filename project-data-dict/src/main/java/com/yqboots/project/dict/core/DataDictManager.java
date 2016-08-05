package com.yqboots.project.dict.core;

import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
public interface DataDictManager {
    List<DataDict> getDataDicts(String name);

    String getText(String name, String value);

    String getText(final String name, final String value, boolean valueIncluded );
}
