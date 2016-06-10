package com.yqboots.prototype.core.support;

import com.yqboots.prototype.core.builder.FileBuilder;
import org.apache.velocity.app.VelocityEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-08.
 */
public class CustomVelocityEngine extends VelocityEngine {
    private List<FileBuilder> builders = new ArrayList<>();

    public CustomVelocityEngine(final List<FileBuilder> builders) {
        super();
        this.builders = builders;
    }

    public List<FileBuilder> getBuilders() {
        return builders;
    }
}