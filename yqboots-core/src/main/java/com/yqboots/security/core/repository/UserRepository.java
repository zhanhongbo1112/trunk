package com.yqboots.security.core.repository;

import com.yqboots.security.core.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015-12-13.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    @Transactional
    @Query("delete from #{#entityName} where username = ?1")
    @Modifying
    void delete(String username);

    @Query("select case when count(0) > 0 then true else false end from #{#entityName} where username = ?1")
    boolean exists(String username);

    List<User> findByUsernameIn(final Collection<String> usernames);

    List<User> findByRolesPath(final String rolePath);

    List<User> findByRolesPathIn(final Collection<String> rolePaths);

    List<User> findByGroupsPath(final String groupPath);

    List<User> findByGroupsPathIn(final Collection<String> groupPaths);

    List<User> findByUsernameLikeIgnoreCase(String usernameFilter);

    Page<User> findByUsernameLikeIgnoreCase(String usernameFilter, Pageable pageable);
}
