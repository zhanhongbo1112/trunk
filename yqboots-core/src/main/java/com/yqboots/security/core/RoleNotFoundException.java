package com.yqboots.security.core;

/**
 * Created by Administrator on 2016-01-17.
 */
@SuppressWarnings("serial")
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
