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
package com.yqboots.project.security.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Role manager.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public interface RoleManager {
    void addRole(Role role) throws RoleExistsException;

    void addUsers(String path, String... usernames) throws RoleNotFoundException;

    void addGroups(String path, String... groupPaths) throws RoleNotFoundException;

    void updateRole(Role role) throws RoleNotFoundException;

    void updateUsers(String path, String... usernames) throws RoleNotFoundException;

    void updateGroups(String path, String... groupPaths) throws RoleNotFoundException;

    void removeRole(String path) throws RoleNotFoundException;

    void removeUsers(String path, String... usernames) throws RoleNotFoundException;

    void removeGroups(String path, String... groupPaths) throws RoleNotFoundException;

    boolean hasRole(String path);

    Role findRole(String path) throws RoleNotFoundException;

    Page<Role> findRoles(String pathFilter, Pageable pageable);

    Page<User> findAllUsers(Pageable pageable);

    List<User> findRoleUsers(String path);

    Page<Group> findAllGroups(Pageable pageable);

    List<Group> findRoleGroups(String path);
}
