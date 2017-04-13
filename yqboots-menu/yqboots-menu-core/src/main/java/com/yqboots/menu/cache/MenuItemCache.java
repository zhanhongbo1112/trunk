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
package com.yqboots.menu.cache;

import com.yqboots.menu.core.MenuItem;

import java.util.List;

/**
 * The cache for {@code com.yqboots.menu.core.MenuItem}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
public interface MenuItemCache {
    /**
     * Initialize the cache.
     *
     * @param key       the caching key
     * @param menuItems list of menu item for the key
     */
    void initialize(String key, List<MenuItem> menuItems);

    /**
     * Puts to the cache.
     *
     * @param menuItem menuItem
     */
    void put(MenuItem menuItem);

    /**
     * Evicts from the cache.
     *
     * @param name name
     */
    void evict(String name);

    /**
     * Gets from cache.
     *
     * @param name actual name
     * @return list of menuItem
     */
    MenuItem get(String name);

    /**
     * Gets from cache.
     *
     * @return list of menuItem
     */
    List<MenuItem> getAll();
}
