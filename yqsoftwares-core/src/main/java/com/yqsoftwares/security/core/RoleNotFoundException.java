package com.yqsoftwares.security.core;

/**
 * Created by Administrator on 2016-01-17.
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
