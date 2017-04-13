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

import java.util.ArrayList;
import java.util.List;

/**
 * The default cache for {@code com.yqboots.menu.core.MenuItem}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
public class NullMenuItemCache implements MenuItemCache {
    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(String key, List<MenuItem> menuItems) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(MenuItem menuItem) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evict(String name) {
        // DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem get(String name) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItem> getAll() {
        return new ArrayList<>();
    }
}
