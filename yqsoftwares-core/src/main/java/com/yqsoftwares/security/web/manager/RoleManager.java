package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.core.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
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

    void addRole(Role role, Collection<String> usernames, Collection<String> groupPaths) throws RoleExistsException;

    void updateRole(Role role, Collection<String> usernames, Collection<String> groupPaths) throws RoleExistsException;

    boolean hasRole(String path);

    Role findRole(String path) throws RoleNotFoundException;

    Page<Role> findRoles(String pathFilter, Pageable pageable);

    Page<User> findAllUsers(Pageable pageable);

    Page<Group> findAllGroups(Pageable pageable);
}
