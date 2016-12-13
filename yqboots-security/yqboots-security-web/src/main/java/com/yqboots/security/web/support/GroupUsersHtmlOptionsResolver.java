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

import com.yqboots.security.core.Group;
import com.yqboots.security.core.GroupManager;
import com.yqboots.security.core.User;
import com.yqboots.security.web.support.consumer.UserToHtmlOptionConsumer;
import com.yqboots.web.thymeleaf.support.HtmlOption;
import com.yqboots.web.thymeleaf.support.AbstractHtmlOptionsResolver;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves {@link User}s of a specified {@link Group}<br/>
 * <p>for the implementation, the first attribute should be the specified path of the group.</p>
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class GroupUsersHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    /**
     * name key: GROUP_USERS
     */
    private static final String NAME_KEY = "GROUP_USERS";

    /**
     * GroupManager.
     */
    private final GroupManager groupManager;

    /**
     * Constructs {@link GroupUsersHtmlOptionsResolver}.
     *
     * @param groupManager groupManager
     */
    @Autowired
    public GroupUsersHtmlOptionsResolver(final GroupManager groupManager) {
        super(NAME_KEY);
        this.groupManager = groupManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        List<HtmlOption> results = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(attributes) && attributes[0] != null) {
            // attributes[0] is path
            List<User> users = groupManager.findGroupUsers(attributes[0]);
            users.forEach(new UserToHtmlOptionConsumer(name, results));
        }

        return results;
    }
}
