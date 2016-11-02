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
import com.yqboots.project.security.core.Role;
import com.yqboots.project.security.core.RoleManager;
import com.yqboots.project.security.web.support.consumer.RoleToDataDictConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves all {@link Role}s
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class AllRolesDataDictResolver extends AbstractDataDictResolver {
    /**
     * name key: ALL_ROLES
     */
    private static final String NAME_KEY = "ALL_ROLES";

    /**
     * RoleManager
     */
    private final RoleManager roleManager;

    /**
     * Constructs <code>AllRolesDataDictResolver</code>.
     *
     * @param roleManager roleManager
     */
    @Autowired
    public AllRolesDataDictResolver(final RoleManager roleManager) {
        super(NAME_KEY);
        this.roleManager = roleManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(String... attributes) {
        final List<DataDict> results = new ArrayList<>();

        List<Role> roles = roleManager.findAllRoles();
        roles.forEach(new RoleToDataDictConsumer(getName(), results));

        return results;
    }
}
