package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.security.core.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-13.
 */
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Group findByPath(String path);

    @Transactional
    @Query("delete from #{#entityName} where path = ?1")
    @Modifying
    void delete(final String path);

    List<Group> findByUsersUsername(final String username);

    List<Group> findByUsersUsernameIn(final Set<String> username);

    List<Group> findByRolesPath(final String rolePath);

    List<Group> findByRolesPathIn(final Set<String> rolePath);

    List<Group> findByPathIn(Set<String> paths);

    List<Group> findByPathLikeIgnoreCase(String pathFilter);
}
