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
package com.yqboots.prototype.project.core;

import com.yqboots.prototype.project.core.builder.FileBuilder;
import com.yqboots.prototype.project.core.velocity.CustomVelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-05-28.
 */
public class MavenProjectInitializer implements ProjectInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(MavenProjectInitializer.class);

    private VelocityEngine velocityEngine;

    public MavenProjectInitializer(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override
    public void startup(ProjectContext context) throws IOException {
        ProjectMetadata metadata = context.getMetadata();

        final VelocityContext velocityContext = new VelocityContext();
        Template template;
        Writer writer = null;

        velocityContext.put(ProjectMetadata.KEY, metadata);

        for (Map.Entry<String, String> entry : resolveTemplates().entrySet()) {
            if (!velocityEngine.resourceExists(entry.getKey())) {
                LOG.warn("Template {0} not found, ignore...", entry.getKey());
                continue;
            }

            template = getVelocityEngine().getTemplate(entry.getKey());

            try {

                CustomVelocityEngine engine = (CustomVelocityEngine) velocityEngine;
                Map<String, FileBuilder> builders = engine.getBuilders();
                if (!builders.containsKey(entry.getKey())) {
                    LOG.warn("Template {0} not found in builder, ignore...", entry.getKey());
                    continue;
                }

                FileBuilder builder = builders.get(entry.getKey());
                // TODO: build target file
                writer = new FileWriter(entry.getValue());

                template.merge(velocityContext, writer);
                writer.flush();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    private Map<String, String> resolveTemplates() {
        final Map<String, String> results = new HashMap<>();

        results.put("pom.xml.vm", "D:\\pom.xml");

        return results;
    }

    protected VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
}
