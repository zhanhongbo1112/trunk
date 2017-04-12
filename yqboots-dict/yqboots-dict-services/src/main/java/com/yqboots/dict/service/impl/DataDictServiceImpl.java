/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.dict.service.impl;

import com.yqboots.core.util.DBUtils;
import com.yqboots.dict.cache.DataDictCache;
import com.yqboots.dict.cache.impl.NullDataDictCache;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDictExistsException;
import com.yqboots.dict.service.DataDictService;
import com.yqboots.dict.service.repository.DataDictRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

/**
 * Manages the Data Dictionary the project has.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Service
@Transactional(readOnly = true)
public class DataDictServiceImpl implements DataDictService {
    private static final Logger LOG = LoggerFactory.getLogger(DataDictServiceImpl.class);

    @Autowired
    private DataDictRepository dataDictRepository;

    @Autowired
    private DataDictCache cache = new NullDataDictCache();

    @PostConstruct
    protected void initialize() {
        final List<String> names = dataDictRepository.findAllDataDictNames();
        for (final String name : names) {
            cache.initialize(name, dataDictRepository.findByNameOrderByText(name));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDict getDataDict(final Long id) {
        return dataDictRepository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DataDict> getDataDicts(final String wildcardName, final Pageable pageable) {
        final String searchStr = StringUtils.trim(StringUtils.defaultString(wildcardName));

        return dataDictRepository.findByNameLikeIgnoreCase(DBUtils.wildcard(searchStr), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(final String name) {
        Assert.hasText(name, "name is required");

        return getDataDicts(name, LocaleContextHolder.getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(final String name, final Locale locale) {
        Assert.hasText(name, "name is required");
        Assert.notNull(locale, "locale is required");

        List<DataDict> results = cache.get(name, locale);
        if (!results.isEmpty()) {
            return results;
        }

        results = dataDictRepository.findByNameOrderByText(cache.getCachedKey(name, locale));
        if (results.isEmpty()) { // fall back to default
            LOG.warn("data dicts for the specified locale {} of name {} did not set", locale, name);
            results = dataDictRepository.findByNameOrderByText(name);
        }

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final String name, final String value) {
        return getText(name, value, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final String name, final String value, boolean valueIncluded) {
        final List<DataDict> all = getDataDicts(name, LocaleContextHolder.getLocale());

        final DataDict item = (DataDict) CollectionUtils.find(all, o -> {
            final DataDict dict = (DataDict) o;
            return StringUtils.equals(dict.getValue(), value);
        });

        if (item == null) {
            LOG.warn("No data dict for {} with value [{}]", name, value);
            return value;
        }

        return valueIncluded ? StringUtils.join(new String[]{item.getValue(), item.getText()}, " - ") : item.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DataDict update(final DataDict entity) throws DataDictExistsException {
        DataDict result;
        if (!entity.isNew()) {
            result = dataDictRepository.save(entity);

            cache.put(result);

            return result;
        }

        Assert.hasText(entity.getName(), "name is required");
        final DataDict existed = dataDictRepository.findByNameAndValue(entity.getName(), entity.getValue());
        if (existed != null) {
            throw new DataDictExistsException("The DataDict has already existed");
        }

        result = dataDictRepository.save(entity);
        cache.put(result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(final Long id) {
        DataDict dict = dataDictRepository.findOne(id);
        if (dict != null) {
            dataDictRepository.delete(dict);
            cache.evict(dict);
        }
    }
}
