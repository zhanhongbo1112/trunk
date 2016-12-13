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
package com.yqboots.menu.context;

import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.repository.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * Caches all menu items on startup.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class MenuItemCacheInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemCacheInitializer.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext ctx = event.getApplicationContext();

        try {
            final CacheManager cacheManager = ctx.getBean(CacheManager.class);
            final Cache cache = cacheManager.getCache("menus");

            final MenuItemRepository menuItemRepository = ctx.getBean(MenuItemRepository.class);
            final List<MenuItem> menuItems = menuItemRepository.findAll();
            for (final MenuItem menuItem : menuItems) {
                cache.put(menuItem.getName(), menuItem);
            }
        } catch (BeansException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
