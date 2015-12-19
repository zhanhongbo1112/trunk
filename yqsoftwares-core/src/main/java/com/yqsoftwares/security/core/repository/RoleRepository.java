package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.security.core.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-13.
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findByPath(String path);

    @Transactional
    @Query("delete from #{#entityName} where path = ?1")
    @Modifying
    void delete(final String path);

    List<Role> findByUsersUsername(final String username);

    List<Role> findByUsersUsernameIn(final Collection<String> usernames);

    List<Role> findByGroupsPath(final String groupPath);

    List<Role> findByGroupsPathIn(final Collection<String> groupPaths);

    List<Role> findByPathIn(Collection<String> paths);

    List<Role> findByPathLikeIgnoreCase(String pathFilter);
}
