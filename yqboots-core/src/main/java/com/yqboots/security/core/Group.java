package com.yqboots.security.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2015-12-13.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SEC_GROUP", uniqueConstraints = {@UniqueConstraint(name = "UN_GROUP_PATH", columnNames = {"PATH"})})
public class Group extends AbstractPersistable<Long> {
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

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "SEC_GROUP_ROLES", joinColumns = @JoinColumn(name = "GROUP_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @JsonIgnore
    private Set<Role> roles;

    @ManyToMany(mappedBy = "groups", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<User> users;

    public Group() {
        super();
    }

    public Group(String path) {
        super();
        this.path = path;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
