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

import com.yqboots.prototype.core.builder.FileBuilder;
import com.yqboots.prototype.core.support.CustomVelocityEngine;
import com.yqboots.prototype.project.autoconfigure.ProjectProperties;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by Administrator on 2016-05-28.
 */
public class ProjectInitializerImpl implements ProjectInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectInitializerImpl.class);

    private CustomVelocityEngine velocityEngine;

    private ProjectProperties properties;

    public ProjectInitializerImpl(CustomVelocityEngine velocityEngine, ProjectProperties properties) {
        this.velocityEngine = velocityEngine;
        this.properties = properties;
    }

    @Override
    public void startup(ProjectContext context) throws IOException {
        Path sourcePath = Paths.get(properties.getSourcePath());
        if (!Files.exists(sourcePath)) {
            throw new FileSystemNotFoundException("The source path not found, " + properties.getSourcePath());
        }

        Path targetPath = Paths.get(properties.getTargetPath() + File.pathSeparator + System.currentTimeMillis()
                + File.pathSeparator + context.getMetadata().getName());
        if (!Files.exists(targetPath)) {
            LOG.info("Creating the target path: {}", targetPath);
            targetPath = Files.createDirectories(targetPath);
        }

        // copy shared resources to target path
        FileUtils.copyDirectory(sourcePath.toFile(), targetPath.toFile());

        ProjectMetadata metadata = context.getMetadata();

        final VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(ProjectMetadata.KEY, metadata);

        Template template;
        Writer writer = null;

        final Map<String, FileBuilder> builders = getVelocityEngine().getBuilders();
        for (Map.Entry<String, FileBuilder> entry : builders.entrySet()) {
            if (!getVelocityEngine().resourceExists(entry.getKey())) {
                LOG.warn("Template {0} not found, ignore...", entry.getKey());
                continue;
            }

            template = getVelocityEngine().getTemplate(entry.getKey());

            try {
                FileBuilder builder = entry.getValue();
                // TODO: retrieve the root path of the target project
                writer = new FileWriter(builder.getFile("TODO: root path"));

                template.merge(velocityContext, writer);
                writer.flush();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

        // compress to one file for downloading
        compressTargetResources(targetPath);
    }

    protected CustomVelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * Compress the resources of target path.
     *
     * @param targetDir the target directory
     */
    private void compressTargetResources(Path targetDir) {

    }
}
