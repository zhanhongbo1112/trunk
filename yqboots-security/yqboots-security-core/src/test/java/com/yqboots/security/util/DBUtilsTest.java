package com.yqboots.security.util;

import org.junit.Test;

public class DBUtilsTest {
    @Test
    public void generateHashCode() {
        System.out.println("/security/user".hashCode());
        System.out.println("/security/group".hashCode());
        System.out.println("/security/role".hashCode());
    }
}