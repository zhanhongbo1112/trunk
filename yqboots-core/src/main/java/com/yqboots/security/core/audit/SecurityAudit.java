package com.yqboots.security.core.audit;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Administrator on 2015-12-15.
 */
@Entity
@DiscriminatorValue("SECURITY")
public class SecurityAudit extends Audit {
    public static final int CODE_ADD_USER = 1;
    public static final int CODE_ADD_GROUPS_TO_USER = 10;
    public static final int CODE_ADD_ROLES_TO_USER = 11;
    public static final int CODE_UPDATE_USER = 2;
    public static final int CODE_UPDATE_GROUPS_OF_USER = 20;
    public static final int CODE_UPDATE_ROLES_OF_USER = 21;
    public static final int CODE_REMOVE_USER = 3;
    public static final int CODE_REMOVE_GROUPS_FROM_USER = 30;
    public static final int CODE_REMOVE_ROLES_FROM_USER = 31;

    public static final int CODE_ADD_GROUP = 4;
    public static final int CODE_ADD_USERS_TO_GROUP = 40;
    public static final int CODE_ADD_ROLES_TO_GROUP = 41;
    public static final int CODE_UPDATE_GROUP = 5;
    public static final int CODE_UPDATE_USERS_OF_GROUP = 50;
    public static final int CODE_UPDATE_ROLES_OF_GROUP = 51;
    public static final int CODE_REMOVE_GROUP = 6;
    public static final int CODE_REMOVE_USERS_FROM_GROUP = 60;
    public static final int CODE_REMOVE_ROLES_FROM_GROUP = 61;

    public static final int CODE_ADD_ROLE = 7;
    public static final int CODE_ADD_USERS_TO_ROLE = 70;
    public static final int CODE_ADD_GROUPS_TO_ROLE = 71;
    public static final int CODE_UPDATE_ROLE = 8;
    public static final int CODE_UPDATE_USERS_OF_ROLE = 80;
    public static final int CODE_UPDATE_GROUPS_OF_ROLE = 81;
    public static final int CODE_REMOVE_ROLE = 9;
    public static final int CODE_REMOVE_USERS_FROM_ROLE = 90;
    public static final int CODE_REMOVE_GROUPS_FROM_ROLE = 91;

    @Column(name = "TARGET", nullable = false)
    private String target;

    public SecurityAudit(int code) {
        super(code);
    }

    private SecurityAudit() {
        super();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
