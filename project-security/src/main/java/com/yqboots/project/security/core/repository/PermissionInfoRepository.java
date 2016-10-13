package com.yqboots.project.security.core.repository;

import com.yqboots.project.security.core.PermissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
 * {@link com.yqboots.project.security.core.PermissionInfo} repository.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface PermissionInfoRepository extends JpaRepository<PermissionInfo, Long>, JpaSpecificationExecutor<PermissionInfo> {
    /**
     * Finds by path and type.
     *
     * @param path path
     * @param type type
     * @return list of PermissionInfo
     */
    List<PermissionInfo> findByPathAndType(String path, String type);

    /**
     * Finds by path of roles.
     *
     * @param rolePath path of the role
     * @return list of PermissionInfo
     */
    List<PermissionInfo> findByRolesPath(final String rolePath);

    /**
     * Finds by path of roles.
     *
     * @param rolePaths path of the roles
     * @return list of PermissionInfo
     */
    List<PermissionInfo> findByRolesPathIn(final Collection<String> rolePaths);
}
