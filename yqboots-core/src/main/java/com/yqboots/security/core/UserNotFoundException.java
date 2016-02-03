package com.yqboots.security.core;

/**
 * Created by Administrator on 2015-12-26.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
