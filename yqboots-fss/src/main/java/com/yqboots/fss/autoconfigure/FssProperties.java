package com.yqboots.fss.autoconfigure;

import com.yqboots.fss.core.Root;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016-05-18.
 */
@ConfigurationProperties(prefix = "yqboots.fss")
public class FssProperties {
    private Root root;

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
