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
package com.yqboots.dict.cache;

import com.yqboots.dict.core.DataDict;

import java.util.List;
import java.util.Locale;

/**
 * The cache for {@code com.yqboots.dict.core.DataDict}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
public interface DataDictCache {
    /**
     * Initialize the cache.
     *
     * @param key       the caching key
     * @param dataDicts list of data dict for the key
     */
    void initialize(String key, List<DataDict> dataDicts);

    /**
     * Puts to the cache.
     *
     * @param dataDict data dict
     */
    void put(DataDict dataDict);

    /**
     * Evicts from the cache.
     *
     * @param dataDict data dict
     */
    void evict(DataDict dataDict);

    /**
     * Gets from cache.
     *
     * @param name   actual name
     * @param locale locale
     * @return list of data dict
     */
    List<DataDict> get(String name, Locale locale);

    /**
     * Gets the key for the specified locale.
     *
     * @param name   name
     * @param locale locale
     * @return the localed key
     */
    default String getCachedKey(final String name, final Locale locale) {
        return name + "_" + locale.toString();
    }
}
