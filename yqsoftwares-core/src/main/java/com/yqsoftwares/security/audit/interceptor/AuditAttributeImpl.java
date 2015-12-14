package com.yqsoftwares.security.audit.interceptor;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditAttributeImpl implements AuditAttribute {
    private int code;

    public AuditAttributeImpl(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
