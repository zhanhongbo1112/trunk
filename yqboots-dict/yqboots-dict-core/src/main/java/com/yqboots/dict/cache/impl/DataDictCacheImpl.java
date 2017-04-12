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
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The cache implementation for data dict.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Component
public class DataDictCacheImpl implements DataDictCache {
    private static final String CACHE_NAME = "dicts";

    private final ConcurrentMapCache cache = new ConcurrentMapCache(CACHE_NAME, false);

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final String key, final List<DataDict> dicts) {
        cache.put(key, dicts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final DataDict entity) {
        final Cache.ValueWrapper wrapper = cache.get(entity.getName());
        if (wrapper == null) {
            final List<DataDict> dicts = new ArrayList<>();
            dicts.add(entity);
            cache.put(entity.getName(), dicts);
        } else {
            final List<DataDict> dicts = (List<DataDict>) wrapper.get();
            dicts.removeIf(new DataDictEqualsPredicate(entity));
            dicts.add(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evict(final DataDict entity) {
        final Cache.ValueWrapper wrapper = cache.get(entity.getName());
        if (wrapper != null) {
            final List<DataDict> dicts = (List<DataDict>) wrapper.get();
            dicts.removeIf(new DataDictEqualsPredicate(entity));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> get(final String name, final Locale locale) {
        List<DataDict> results = new ArrayList<>();

        Cache.ValueWrapper wrapper = cache.get(getCachedKey(name, locale));
        if (wrapper != null) {
            results = (List<DataDict>) wrapper.get();
        } else {// fall back to name
            wrapper = cache.get(name);
            if (wrapper != null) {
                results = (List<DataDict>) wrapper.get();
            }
        }

        return results;
    }

    private static class DataDictEqualsPredicate implements Predicate<DataDict> {
        private final DataDict dataDict;

        DataDictEqualsPredicate(final DataDict dataDict) {
            this.dataDict = dataDict;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean test(final DataDict dict) {
            return Objects.equals(dataDict.getId(), dict.getId());
        }
    }
}
