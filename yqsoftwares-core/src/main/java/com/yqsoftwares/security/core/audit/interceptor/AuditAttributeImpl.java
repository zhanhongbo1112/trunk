package com.yqsoftwares.security.core.audit.interceptor;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditAttributeImpl implements AuditAttribute {
    private int code;

    public AuditAttributeImpl(int codes) {
        this.code = codes;
    }

    public int getCode() {
        return code;
    }
}
