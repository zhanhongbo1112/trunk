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
 * User manager.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public interface UserManager {
    void addUser(User user) throws UserExistsException;

    void updateUser(User user) throws UserNotFoundException;

    void removeUser(String username) throws UserNotFoundException;

    boolean hasUser(String username);

    User findUser(String username) throws UserNotFoundException;

    Page<User> findUsers(String usernameFilter, Pageable pageable);

    void addGroups(String username, String... groups) throws UserExistsException;

    void updateGroups(String username, String... groups) throws UserNotFoundException;

    void removeGroups(String username, String... groups) throws UserNotFoundException;

    void addRoles(String username, String... roles) throws UserExistsException;

    void updateRoles(String username, String... roles) throws UserNotFoundException;

    void removeRoles(String username, String... roles) throws UserNotFoundException;

    Page<Group> findAllGroups(Pageable pageable);

    List<Group> findUserGroups(String username);

    Page<Role> findAllRoles(Pageable pageable);

    List<Role> findUserRoles(String username);
}
