package com.yqboots.project.security.util;

import org.junit.Test;

public class DBUtilsTest {
    @Test
    public void generateHashCode() {
        System.out.println("/project/security/user".hashCode());
        System.out.println("/project/security/group".hashCode());
        System.out.println("/project/security/role".hashCode());
    }
}