package com.yqboots.security.core;

/**
 * Created by Administrator on 2016-01-17.
 */
@SuppressWarnings("serial")
public class GroupExistsException extends RuntimeException {
    public GroupExistsException(String message) {
        super(message);
    }
}
