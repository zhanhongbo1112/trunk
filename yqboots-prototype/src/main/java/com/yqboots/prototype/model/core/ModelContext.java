package com.yqboots.prototype.model.core;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-06-09.
 */
@SuppressWarnings("serial")
public class ModelContext implements Serializable {
    private ModelMetadata metadata;

    public ModelMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final ModelMetadata metadata) {
        this.metadata = metadata;
    }
}
