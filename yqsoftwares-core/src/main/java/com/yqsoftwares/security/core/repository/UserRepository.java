package com.yqsoftwares.security.core.repository;

import com.yqsoftwares.security.core.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-13.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    @Query("delete from #{#entityName} where username = ?1")
    @Modifying
    void delete(String username);

    List<User> findByUsernameIn(final Set<String> usernames);

    List<User> findByRolesPath(final String rolePath);

    List<User> findByGroupsPath(final String groupPath);

    List<User> findByUsernameLikeIgnoreCase(String usernameFilter);
}
