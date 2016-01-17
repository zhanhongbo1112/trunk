package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.core.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
 */
public interface UserManager {
    void addUser(User entity) throws UserExistsException;

    void addGroups(User user, String... groups) throws UserExistsException;

    void addRoles(User user, String... roles) throws UserExistsException;

    void updateUser(User entity) throws UserNotFoundException;

    void updateGroups(User user, String... groups) throws UserNotFoundException;

    void updateRoles(User user, String... roles) throws UserNotFoundException;

    void removeUser(String username) throws UserNotFoundException;

    void removeGroups(User user, String... groups) throws UserNotFoundException;

    void removeRoles(User user, String... roles) throws UserNotFoundException;

    void addUser(User entity, Collection<String> groups, Collection<String> roles) throws UserExistsException;

    void updateUser(User entity, Collection<String> groups, Collection<String> roles) throws UserNotFoundException;

    boolean hasUser(String username);

    User findUser(String username) throws UserNotFoundException;

    Page<User> findUsers(String usernameFilter, Pageable pageable);

    List<Group> findAllGroups(Pageable pageable);

    List<Role> findAllRoles(Pageable pageable);

    void updateState(String username, boolean enabled) throws UserNotFoundException;
}
