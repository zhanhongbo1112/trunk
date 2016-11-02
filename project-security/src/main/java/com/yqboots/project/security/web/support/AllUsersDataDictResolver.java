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
package com.yqboots.project.security.web.support;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.support.AbstractDataDictResolver;
import com.yqboots.project.security.core.User;
import com.yqboots.project.security.core.UserManager;
import com.yqboots.project.security.web.support.consumer.UserToDataDictConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves all {@link User}s
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class AllUsersDataDictResolver extends AbstractDataDictResolver {
    /**
     * name key: ALL_USERS
     */
    private static final String NAME_KEY = "ALL_USERS";

    /**
     * GroupManager
     */
    private final UserManager userManager;

    /**
     * Constructs <code>AllUsersDataDictResolver</code>.
     *
     * @param userManager userManager
     */
    @Autowired
    public AllUsersDataDictResolver(final UserManager userManager) {
        super(NAME_KEY);
        this.userManager = userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(String... attributes) {
        final List<DataDict> results = new ArrayList<>();

        List<User> groups = userManager.findAllUsers();
        groups.forEach(new UserToDataDictConsumer(getName(), results));

        return results;
    }
}
