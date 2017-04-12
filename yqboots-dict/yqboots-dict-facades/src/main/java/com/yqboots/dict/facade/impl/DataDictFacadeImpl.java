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
package com.yqboots.dict.facade.impl;

import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDictExistsException;
import com.yqboots.dict.facade.DataDictFacade;
import com.yqboots.dict.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * Facades the Data Dictionary the project has.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Component
public class DataDictFacadeImpl implements DataDictFacade {
    private final DataDictService dataDictService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    public DataDictFacadeImpl(final DataDictService dataDictService) {
        this.dataDictService = dataDictService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDict getDataDict(final Long id) {
        return dataDictService.getDataDict(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DataDict> getDataDicts(final String wildcardName, final Pageable pageable) {
        return dataDictService.getDataDicts(wildcardName, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(final String name) {
        return getDataDicts(name, LocaleContextHolder.getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(final String name, final Locale locale) {
        return dataDictService.getDataDicts(name, locale);
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
        return dataDictService.getText(name, value, valueIncluded);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDict update(final DataDict entity) throws DataDictExistsException {
        return dataDictService.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Long id) {
        dataDictService.delete(id);
    }

}
