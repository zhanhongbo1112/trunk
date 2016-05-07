package com.yqboots.fss;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-04-27.
 */
@SuppressWarnings("serial")
public class Root implements Serializable {
    private String path;

    public Root(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
