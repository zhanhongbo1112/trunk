package com.yqboots.security.core;

/**
 * Created by Administrator on 2015-12-26.
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
