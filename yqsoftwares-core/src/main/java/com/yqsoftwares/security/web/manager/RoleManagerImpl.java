package com.yqsoftwares.security.web.manager;

import com.yqsoftwares.security.core.*;
import com.yqsoftwares.security.core.repository.GroupRepository;
import com.yqsoftwares.security.core.repository.RoleRepository;
import com.yqsoftwares.security.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleManagerImpl implements RoleManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addRole(Role role) throws RoleExistsException {

    }

    @Override
    public void addUsers(String path, String... usernames) throws RoleExistsException {

    }

    @Override
    public void addGroups(String path, String... groups) throws RoleExistsException {

    }

    @Override
    public void updateRole(Role role) throws RoleNotFoundException {

    }

    @Override
    public void updateUsers(String path, String... usernames) throws RoleNotFoundException {

    }

    @Override
    public void updateGroups(String path, String... groups) throws RoleNotFoundException {

    }

    @Override
    public void removeRole(String path) throws RoleNotFoundException {

    }

    @Override
    public void removeUsers(String path, String... usernames) throws RoleNotFoundException {

    }

    @Override
    public void removeGroups(String path, String... roles) throws RoleNotFoundException {

    }

    @Override
    public void addRole(Role role, Collection<String> usernames, Collection<String> groups) throws RoleExistsException {

    }

    @Override
    public void updateRole(Role role, Collection<String> usernames, Collection<String> groups) throws RoleExistsException {

    }

    @Override
    public boolean hasRole(String path) {
        return false;
    }

    @Override
    public Role findRole(String path) throws RoleNotFoundException {
        return null;
    }

    @Override
    public Page<Role> findRoles(String pathFilter, Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public List<Group> findAllGroups(Pageable pageable) {
        return null;
    }
}
