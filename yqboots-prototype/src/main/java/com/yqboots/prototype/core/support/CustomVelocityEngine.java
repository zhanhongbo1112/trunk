package com.yqboots.prototype.core.support;

import com.yqboots.prototype.core.builder.FileBuilder;
import org.apache.velocity.app.VelocityEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-06-08.
 */
public class CustomVelocityEngine extends VelocityEngine {
    private Map<String, FileBuilder> builders = new HashMap<>();

    public Map<String, FileBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<String, FileBuilder> builders) {
        this.builders = builders;
    }
}