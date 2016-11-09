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
package com.yqboots.security.core.repository;

import com.yqboots.security.core.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for permission.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Repository
public class PermissionRepository {
    private static final String PERMISSIONS_BY_ROLE_SQL = "select sid.sid, aoi.object_id_identity, ac.class, ae.mask\n" +
            "from acl_sid as sid, acl_class ac, acl_object_identity aoi\n" +
            "left outer join acl_entry ae\n" +
            "on ae.acl_object_identity = aoi.id and ae.sid = sid.id\n" +
            "where sid.sid=? and sid.id = aoi.owner_sid and ac.id = aoi.object_id_class";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Permission> findBySid(final String sid) {
        return jdbcTemplate.query(PERMISSIONS_BY_ROLE_SQL, ps -> ps.setString(1, sid), (rs, rowNum) -> {
            Permission result = new Permission();
            result.setSid(rs.getString(1));
            result.setPath(rs.getLong(2));
            result.setClazz(rs.getString(3));
            result.setMask(rs.getInt(4));

            return result;
        });
    }
}
