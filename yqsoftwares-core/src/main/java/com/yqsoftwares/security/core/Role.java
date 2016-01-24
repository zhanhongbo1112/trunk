package com.yqsoftwares.security.core;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-13.
 */
@Entity
@Table(name = "SEC_ROLE", uniqueConstraints = {@UniqueConstraint(name = "UN_ROLE_PATH", columnNames = {"PATH"})})
public class Role extends AbstractPersistable<Long> implements GrantedAuthority {
    @NotEmpty
    @Length(max = 255)
    @Column(unique = true, length = 255, nullable = false)
    @NaturalId
    private String path;

    @NotEmpty
    @Length(max = 64)
    @Column(length = 64)
    private String alias;

    @Length(max = 255)
    @Column(length = 255)
    private String description;

    /**
     * Note: specified <code>mappedBy</code> to avoid defining in two sides of
     *
     * @JoinTable(name ="SEC_USER_ROLE", joinColumns = @JoinColumn(name =
     * "ROLE_ID"),inverseJoinColumns = @JoinColumn(name =
     * "USER_ID"))
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(mappedBy = "roles")
    private Set<Group> groups;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String getAuthority() {
        return this.path;
    }
}
