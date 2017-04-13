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
package com.yqboots.menu.facade.impl;

import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.service.MenuItemService;
import com.yqboots.menu.facade.MenuItemFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * It Manages the MenuItem related functions.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Component
public class MenuItemFacadeImpl implements MenuItemFacade {
    @Autowired
    private MenuItemService menuItemService;

    /**
     * {@inheritDoc}
     */
    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<MenuItem> getMenuItems() {
        return menuItemService.getMenuItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<MenuItem> getMenuItems(final String wildcardName, final Pageable pageable) {
        return menuItemService.getMenuItems(wildcardName, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(final Long id) {
        return menuItemService.getMenuItem(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(final String name) {
        return menuItemService.getMenuItem(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public MenuItem update(final MenuItem entity) {
        return menuItemService.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(final Long id) {
        menuItemService.delete(id);
    }
}
