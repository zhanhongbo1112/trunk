package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import com.yqsoftwares.security.core.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
 */
public interface UserManager {
    void addUser(User entity, Collection<String> groups, Collection<String> roles);

    void updateUser(User entity, Collection<String> groups, Collection<String> roles);

    User findUser(String username) throws UserNotFoundException;

    Page<User> findUsers(String usernameFilter, Pageable pageable);

    List<Group> findAllGroups(Pageable pageable);

    List<Role> findAllRoles(Pageable pageable);

    void updateState(String username, boolean enabled);
}
