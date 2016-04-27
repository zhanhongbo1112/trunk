package com.yqboots.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2015-12-14.
 */
public final class DBUtils {
    public static final String WILDCARD_ALL = "%";

    private DBUtils() {
    }

    public static String wildcard(final String source) {
        String result = WILDCARD_ALL;
        if (!StringUtils.isEmpty(source)) {
            result = WILDCARD_ALL + source + WILDCARD_ALL;
        }

        return result;
    }
}
