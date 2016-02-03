package com.yqsoftwares.security.core;

import com.yqsoftwares.security.core.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
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
