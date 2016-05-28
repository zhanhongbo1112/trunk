/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.prototype.project.velocity;

import com.yqboots.fss.core.Root;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.File;
import java.util.Iterator;

/**
 * Created by Administrator on 2016-05-28.
 */
public class CustomVelocityEngineFactoryBean extends VelocityEngineFactoryBean {
    private Root root;

    private VelocityProperties velocityProperties;

    public CustomVelocityEngineFactoryBean(VelocityProperties velocityProperties) {
        this.velocityProperties = velocityProperties;
    }

    @Override
    public void setResourceLoaderPath(String _resourceLoaderPath) {
        StringBuilder sb = new StringBuilder(StringUtils.defaultIfEmpty(_resourceLoaderPath, StringUtils.EMPTY));

        // dynamically load the resource, and merge to existing resource
        if (root != null) {
            Iterator<File> templates = FileUtils.iterateFiles(new File(root.getPath()),
                    new String[]{velocityProperties.getSuffix().substring(1)}, true);
            while (templates.hasNext()) {
                File template = templates.next();
                sb.append(",file:").append(template.getParent());
            }
        }

        super.setResourceLoaderPath(sb.toString());
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
