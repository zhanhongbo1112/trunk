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
package com.yqboots.project.security.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * PermissionInfo entity.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SEC_PERMISSION_INFO", uniqueConstraints = {@UniqueConstraint(name = "UN_PERM_INFO", columnNames = {"PATH", "TYPE", "PERMISSION"})})
public class PermissionInfo extends AbstractPersistable<Long> {
    @NotEmpty
    @Length(max = 255)
    @Column(length = 255, nullable = false)
    private String path;

    @NotEmpty
    @Length(max = 255)
    @Column(length = 255, nullable = false)
    private String type;

    @NotEmpty
    @Length(max = 255)
    @Column(length = 255, nullable = false)
    private String permission;

    @Length(max = 255)
    @Column(length = 255)
    private String description;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL})
    @JsonIgnore
    private Set<Role> roles;

    public PermissionInfo() {
        super();
    }

    public PermissionInfo(final String path, final String type, final String permission) {
        this();
        this.path = path;
        this.type = type;
        this.permission = permission;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(final String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }
}
