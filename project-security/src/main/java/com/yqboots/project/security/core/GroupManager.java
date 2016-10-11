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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Group manager.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Service
@Transactional(readOnly = true)
public interface GroupManager {
    void addGroup(Group group) throws GroupExistsException;

    void addUsers(String path, String... usernames) throws GroupNotFoundException;

    void addRoles(String path, String... rolePaths) throws GroupNotFoundException;

    void updateGroup(Group group) throws GroupNotFoundException;

    void updateUsers(String path, String... usernames) throws GroupNotFoundException;

    void updateRoles(String path, String... rolePaths) throws GroupNotFoundException;

    void removeGroup(String path) throws GroupNotFoundException;

    void removeUsers(String path, String... usernames) throws GroupNotFoundException;

    void removeRoles(String path, String... roles) throws GroupNotFoundException;

    boolean hasGroup(String path);

    Group findGroup(String path) throws GroupNotFoundException;

    Page<Group> findGroups(String pathFilter, Pageable pageable);

    Page<User> findAllUsers(Pageable pageable);

    List<User> findGroupUsers(String path);

    Page<Role> findAllRoles(Pageable pageable);

    List<Role> findGroupRoles(String path);
}
