package com.yqsoftwares.security.core.audit.interceptor;

/**
 * Created by Administrator on 2015-12-14.
 */
public class AuditAttributeImpl implements AuditAttribute {
    private int[] codes;

    public AuditAttributeImpl(int[] codes) {
        this.codes = codes;
    }

    @Override
    public int[] getCodes() {
        return codes;
    }
}
