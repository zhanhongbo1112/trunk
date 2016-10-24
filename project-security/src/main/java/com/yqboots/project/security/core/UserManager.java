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
    /**
     * Adds new user.
     *
     * @param user user
     * @throws UserExistsException throw when the entity exists
     */
    void addUser(User user) throws UserExistsException;

    /**
     * @param username username
     * @param groupPaths path of groups
     * @param rolePaths  path of roles
     * @throws UserNotFoundException
     */
    void addUser(String username, List<String> groupPaths, List<String> rolePaths) throws UserExistsException;

    /**
     * Adds {@link Group}s to the {@link User}.
     *
     * @param username   username
     * @param groupPaths path of groups
     * @throws UserNotFoundException throw when the entity is not found
     */
    void addGroups(String username, String... groupPaths) throws UserNotFoundException;

    /**
     * Adds {@link Role}s to the {@link User}.
     *
     * @param username  username
     * @param rolePaths path of roles
     * @throws UserNotFoundException throw when the entity is not found
     */
    void addRoles(String username, String... rolePaths) throws UserNotFoundException;

    /**
     * Updates the user.
     *
     * @param user user
     * @throws UserNotFoundException throw when the entity is not found
     */
    void updateUser(User user) throws UserNotFoundException;

    /**
     * @param username username
     * @param groupPaths path of groups
     * @param rolePaths  path of roles
     * @throws UserNotFoundException
     */
    void updateUser(String username, List<String> groupPaths, List<String> rolePaths) throws UserNotFoundException;

    /**
     * Updates {@link Group}s of the {@link User}.
     *
     * @param username   username
     * @param groupPaths path of groups
     * @throws UserNotFoundException throw when the entity is not found
     */
    void updateGroups(String username, String... groupPaths) throws UserNotFoundException;

    /**
     * Updates {@link Role}s of the {@link User}.
     *
     * @param username  username
     * @param rolePaths paths of role
     * @throws UserNotFoundException throw when the entity is not found
     */
    void updateRoles(String username, String... rolePaths) throws UserNotFoundException;

    /**
     * Removes the user.
     *
     * @param id identity of the user
     * @throws UserNotFoundException throw when the entity is not found
     */
    void removeUser(Long id) throws UserNotFoundException;

    /**
     * Removes the user.
     *
     * @param username username
     * @throws UserNotFoundException throw when the entity is not found
     */
    void removeUser(String username) throws UserNotFoundException;

    /**
     * Removes the {@link Group}s from the {@link User}.
     *
     * @param username   username
     * @param groupPaths path of groups
     * @throws UserNotFoundException throw when the entity is not found
     */
    void removeGroups(String username, String... groupPaths) throws UserNotFoundException;

    /**
     * Removes the {@link Role}s from the {@link User}.
     *
     * @param username  username
     * @param rolePaths path of roles
     * @throws GroupNotFoundException throw when the entity is not found
     */
    void removeRoles(String username, String... rolePaths) throws UserNotFoundException;

    /**
     * Checks if the {@link User} exists.
     *
     * @param username username
     * @return true when the group with the specified path exists
     */
    boolean hasUser(String username);

    /**
     * Finds {@link User} by id.
     *
     * @param id identity of the user
     * @return the user
     * @throws RoleNotFoundException throw when the entity is not found
     */
    User findUser(Long id) throws UserNotFoundException;

    /**
     * Finds {@link User} by username.
     *
     * @param id identity of the user
     * @return the user
     * @throws RoleNotFoundException throw when the entity is not found
     */
    User findUserWithGroupsAndRoles(Long id) throws UserNotFoundException;

    /**
     * Finds {@link User} by username.
     *
     * @param username username
     * @return the role
     * @throws RoleNotFoundException throw when the entity is not found
     */
    User findUser(String username) throws UserNotFoundException;

    /**
     * Finds {@link User}s by wildcard condition.
     *
     * @param usernameFilter usernameFilter
     * @param pageable       pageable
     * @return page of users
     */
    Page<User> findUsers(String usernameFilter, Pageable pageable);

    /**
     * Finds all {@link Group}s.
     *
     * @param pageable pageable
     * @return page of groups
     */
    Page<Group> findAllGroups(Pageable pageable);

    /**
     * Finds all {@link Group}s which belong to the {@link User}.
     *
     * @param username username
     * @return list of groups
     */
    List<Group> findUserGroups(String username);

    /**
     * Finds all {@link Role}s.
     *
     * @param pageable pageable
     * @return page of roles
     */
    Page<Role> findAllRoles(Pageable pageable);

    /**
     * Finds all {@link Role}s which belong to the {@link User}.
     *
     * @param username username
     * @return list of roles
     */
    List<Role> findUserRoles(String username);
}
