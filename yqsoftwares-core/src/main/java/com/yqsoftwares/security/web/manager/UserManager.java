package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.core.Group;
import com.yqsoftwares.security.core.Role;
import com.yqsoftwares.security.core.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015-12-19.
 */
public interface UserManager {
    void addUser(User entity, String[] groups, String[] roles);

    void updateUser(User entity, String[] groups, String[] roles);

    Page<User> findUsers(String username, Pageable pageable);

    List<Group> findAllGroups(Pageable pageable);

    List<Role> findAllRoles(Pageable pageable);

    void updateState(String username, boolean enabled);
}
