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
package com.yqboots.menu.cache.impl;

import com.yqboots.menu.cache.MenuItemCache;
import com.yqboots.menu.core.MenuItem;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The default cache for {@code com.yqboots.menu.core.MenuItem}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Component
public class MenuItemCacheImpl implements MenuItemCache {
    private static final String CACHE_NAME = "menus";

    private final ConcurrentMapCache cache = new ConcurrentMapCache(CACHE_NAME, false);

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String key, List<MenuItem> menuItems) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(MenuItem menuItem) {
        cache.put(menuItem.getName(), menuItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evict(String name) {
        cache.evict(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem get(String name) {
        final Cache.ValueWrapper wrapper = cache.get(name);
        if (wrapper != null) {
            return (MenuItem) wrapper.get();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItem> getAll() {
        final Map<Object, Object> nativeCache = cache.getNativeCache();

        return nativeCache.values().stream().map(value -> (MenuItem) value).collect(Collectors.toList());
    }
}
