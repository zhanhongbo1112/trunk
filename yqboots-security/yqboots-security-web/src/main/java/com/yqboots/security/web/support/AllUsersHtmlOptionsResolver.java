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
import com.yqboots.security.core.User;
import com.yqboots.security.core.UserManager;
import com.yqboots.security.web.support.consumer.UserToHtmlOptionConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves all {@link User}s
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AllUsersHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    /**
     * name key: ALL_USERS
     */
    private static final String NAME_KEY = "ALL_USERS";

    /**
     * GroupManager
     */
    private final UserManager userManager;

    /**
     * Constructs {@link AllUsersHtmlOptionsResolver}.
     *
     * @param userManager userManager
     */
    @Autowired
    public AllUsersHtmlOptionsResolver(final UserManager userManager) {
        super(NAME_KEY);
        this.userManager = userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        final List<HtmlOption> results = new ArrayList<>();

        List<User> groups = userManager.findAllUsers();
        groups.forEach(new UserToHtmlOptionConsumer(name, results));

        return results;
    }
}
