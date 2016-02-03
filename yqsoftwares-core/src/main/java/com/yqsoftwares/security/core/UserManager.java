package com.yqsoftwares.security.core;

import com.yqsoftwares.security.core.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
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
