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
package com.yqboots.prototype.project.maven;

import com.yqboots.prototype.project.ProjectInitializer;
import com.yqboots.prototype.project.ProjectMetadata;
import com.yqboots.prototype.project.ProjectType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 2016-05-28.
 */
public class MavenProjectInitializer implements ProjectInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(MavenProjectInitializer.class);

    private VelocityEngine velocityEngine;

    private VelocityProperties velocityProperties;

    public MavenProjectInitializer(VelocityEngine velocityEngine, VelocityProperties velocityProperties) {
        this.velocityEngine = velocityEngine;
        this.velocityProperties = velocityProperties;
    }

    @Override
    public void startup(ProjectMetadata metadata) throws IOException {
        if (metadata.getType() != ProjectType.MAVEN) {
            LOG.info("Not a Maven project [{0}], ignore...", metadata.getType());
            return;
        }

        final VelocityContext context = new VelocityContext();
        Template template;
        Writer writer = null;

        context.put(ProjectMetadata.KEY, metadata);

        Object commaSeparatedPaths = velocityEngine.getProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH);
        for (Map.Entry<String, String> entry : resolveTemplates(commaSeparatedPaths).entrySet()) {
            if (!velocityEngine.resourceExists(entry.getKey())) {
                LOG.warn("Template {0} not found.", entry.getKey());
                continue;
            }

            template = getVelocityEngine().getTemplate(entry.getKey());

            try {
                writer = new FileWriter(entry.getValue());

                template.merge(context, writer);
                writer.flush();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    private Map<String, String> resolveTemplates(Object _paths) {
        final Map<String, String> results = new HashMap<>();

        Vector<String> paths = new Vector<>();
        if (_paths instanceof String) {
            paths.add((String) _paths);
        } else {
            paths = (Vector<String>) _paths;
        }

        for (String path : paths) {
            Iterator<File> templates = FileUtils.iterateFiles(new File(path),
                    new String[]{getVelocityProperties().getSuffix().substring(1)}, false);
            while (templates.hasNext()) {
                File template = templates.next();

                String newFilePath = StringUtils.substringBeforeLast(template.getPath(),
                        getVelocityProperties().getSuffix());
                results.put(template.getName(), newFilePath);
            }
        }

        return results;
    }

    protected VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    protected VelocityProperties getVelocityProperties() {
        return velocityProperties;
    }
}
