/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
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
