package com.yqsoftwares.security.web.model;

import com.yqsoftwares.security.core.User;

/**
 * Created by Administrator on 2016-02-01.
 */
public class UserContext {
    private User user;
    private String[] groups;
    private String[] roles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
