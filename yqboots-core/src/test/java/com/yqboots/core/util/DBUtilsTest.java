package com.yqboots.core.util;

import org.junit.Test;

public class DBUtilsTest {
    @Test
    public void generateHashCode() {
        System.out.println("/".hashCode());
        System.out.println("/projects/framework".hashCode());
        System.out.println("/menu".hashCode());
        System.out.println("/dict".hashCode());
        System.out.println("/fss".hashCode());
        System.out.println("/security/user".hashCode());
        System.out.println("/security/group".hashCode());
        System.out.println("/security/role".hashCode());
        System.out.println("/security/permission".hashCode());
        System.out.println("/security/audit".hashCode());
        System.out.println("/security/audit/history".hashCode());
        System.out.println("/security/audit/session".hashCode());
    }
}