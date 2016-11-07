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
package com.yqboots.security.web.support;

import com.yqboots.core.html.HtmlOption;
import com.yqboots.core.html.support.AbstractHtmlOptionsResolver;
import com.yqboots.security.core.Group;
import com.yqboots.security.core.GroupManager;
import com.yqboots.security.web.support.consumer.GroupToHtmlOptionConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves all {@link com.yqboots.security.core.Group}s
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AllGroupsHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    /**
     * name key: ALL_GROUPS
     */
    private static final String NAME_KEY = "ALL_GROUPS";

    /**
     * GroupManager
     */
    private final GroupManager groupManager;

    /**
     * Constructs {@link AllGroupsHtmlOptionsResolver}.
     *
     * @param groupManager groupManager
     */
    @Autowired
    public AllGroupsHtmlOptionsResolver(final GroupManager groupManager) {
        super(NAME_KEY);
        this.groupManager = groupManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        final List<HtmlOption> results = new ArrayList<>();

        List<Group> groups = groupManager.findAllGroups();
        groups.forEach(new GroupToHtmlOptionConsumer(name, results));

        return results;
    }
}
