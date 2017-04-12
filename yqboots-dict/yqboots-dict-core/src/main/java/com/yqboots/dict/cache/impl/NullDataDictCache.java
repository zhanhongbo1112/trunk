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
package com.yqboots.dict.cache.impl;

import com.yqboots.dict.cache.DataDictCache;
import com.yqboots.dict.core.DataDict;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The default cache for {@code com.yqboots.dict.core.DataDict}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
public class NullDataDictCache implements DataDictCache {
    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String key, List<DataDict> dataDicts) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(DataDict dataDict) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evict(DataDict dataDict) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> get(String name, Locale locale) {
        return new ArrayList<>();
    }
}
