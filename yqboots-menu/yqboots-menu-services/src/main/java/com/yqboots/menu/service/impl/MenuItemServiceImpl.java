/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.menu.service.impl;

import com.yqboots.core.util.DBUtils;
import com.yqboots.menu.cache.MenuItemCache;
import com.yqboots.menu.cache.impl.NullMenuItemCache;
import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.MenuItemExistsException;
import com.yqboots.menu.service.repository.MenuItemRepository;
import com.yqboots.menu.service.MenuItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * It Manages the MenuItem related functions.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired(required = false)
    private MenuItemCache cache = new NullMenuItemCache();

    @PostConstruct
    protected void initialize() {
        final List<MenuItem> menuItems = menuItemRepository.findAll();
        for (final MenuItem menuItem : menuItems) {
            cache.put(menuItem);
        }
    }

    /**
     * {@inheritDoc}
     */
    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<MenuItem> getMenuItems() {
        final List<MenuItem> results = new ArrayList<>();

        results.addAll(cache.getAll());
        results.sort(Comparator.comparing(MenuItem::getSequentialOrder));

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<MenuItem> getMenuItems(final String wildcardName, final Pageable pageable) {
        final String searchStr = StringUtils.trim(StringUtils.defaultString(wildcardName));
        return menuItemRepository.findByNameLikeIgnoreCaseOrderByName(DBUtils.wildcard(searchStr), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(final Long id) {
        return menuItemRepository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(final String name) {
        final MenuItem result = cache.get(name);
        if (result != null) {
            return result;
        }

        return menuItemRepository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public MenuItem update(final MenuItem entity) {
        Assert.hasText(entity.getName(), "name is required");

        MenuItem result;
        if (!entity.isNew()) {
            result = menuItemRepository.save(entity);
        } else {
            final MenuItem existed = menuItemRepository.findByName(entity.getName());
            if (existed != null) {
                throw new MenuItemExistsException("The MenuItem has already existed");
            }

            result = menuItemRepository.save(entity);
        }

        cache.put(result);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(final Long id) {
        final MenuItem menuItem = menuItemRepository.findOne(id);
        if (menuItem != null) {
            final String name = menuItem.getName();
            menuItemRepository.delete(menuItem);
            cache.evict(name);
        }
    }
}
