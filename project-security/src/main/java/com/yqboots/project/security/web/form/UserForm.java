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
package com.yqboots.project.security.web.form;

import java.io.Serializable;

/**
 * User Form.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class UserForm implements Serializable {
    private String username;

    private String[] groups;

    private String[] roles;

    private boolean existed;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(final String[] groups) {
        this.groups = groups;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(final String[] roles) {
        this.roles = roles;
    }

    public boolean isExisted() {
        return existed;
    }

    public void setExisted(final boolean existed) {
        this.existed = existed;
    }
}
