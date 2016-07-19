package com.yqboots.project.dict.core;

import com.yqboots.project.dict.core.repository.DataDictRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
@Service
@Transactional(readOnly = true)
public class DataDictManagerImpl implements DataDictManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDictManagerImpl.class);

    private DataDictRepository dataDictRepository;

    @Autowired
    public DataDictManagerImpl(final DataDictRepository dataDictRepository) {
        this.dataDictRepository = dataDictRepository;
    }

    @Override
    public List<DataDict> getDataDicts(final String name) {
        return dataDictRepository.findByNameOrderByText(name);
    }

    @Override
    public String getText(final String name, final String value) {
        // TODO: add cache
        List<DataDict> all = dataDictRepository.findByNameOrderByText(name);

        // TODO: change to stream api
        List<DataDict> results = (List<DataDict>) CollectionUtils.find(all, new Predicate() {
            @Override
            public boolean evaluate(final Object o) {
                DataDict dict = (DataDict) o;
                return StringUtils.equals(dict.getValue(), value);
            }
        });

        if (results.isEmpty()) {
            LOGGER.warn("No data dict for {} with value [{}]", name, value);
            return StringUtils.EMPTY;
        }

        return results.get(0).getText();
    }

    protected DataDictRepository getDataDictRepository() {
        return dataDictRepository;
    }
}
