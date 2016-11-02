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

import com.yqboots.dict.core.support.AbstractDataDictResolver;
import com.yqboots.dict.core.DataDict;
import com.yqboots.security.core.Role;
import com.yqboots.security.core.User;
import com.yqboots.security.core.UserManager;
import com.yqboots.security.web.support.consumer.RoleToDataDictConsumer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves {@link com.yqboots.security.core.Role}s of a specified {@link User}<br/>
 * <p>for the implementation, the first attribute should be the specified username of the user.</p>
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class UserRolesDataDictResolver extends AbstractDataDictResolver {
    /**
     * name key: USER_ROLES
     */
    private static final String NAME_KEY = "USER_ROLES";

    /**
     * UserManager.
     */
    private final UserManager userManager;

    /**
     * Constructs <code>UserRolesDataDictResolver</code>.
     *
     * @param userManager userManager
     */
    @Autowired
    public UserRolesDataDictResolver(final UserManager userManager) {
        super(NAME_KEY);
        this.userManager = userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(String... attributes) {
        List<DataDict> results = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(attributes) && attributes[0] != null) {
            // attributes[0] is username
            List<Role> roles = userManager.findUserRoles(attributes[0]);
            roles.forEach(new RoleToDataDictConsumer(getName(), results));
        }

        return results;
    }
}
