package com.yqboots.project.dict.core;

import com.yqboots.project.dict.core.repository.DataDictRepository;

/**
 * Created by Administrator on 2016-07-19.
 */
public class DataDictManagerImpl implements DataDictManager {
    private DataDictRepository dataDictRepository;

    public DataDictManagerImpl(final DataDictRepository dataDictRepository) {
        this.dataDictRepository = dataDictRepository;
    }

    protected DataDictRepository getDataDictRepository() {
        return dataDictRepository;
    }
}
