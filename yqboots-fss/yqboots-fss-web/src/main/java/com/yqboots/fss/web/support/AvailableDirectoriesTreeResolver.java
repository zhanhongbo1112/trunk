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
package com.yqboots.fss.web.support;

import com.yqboots.fss.core.FileItemManager;
import com.yqboots.fss.core.support.DirectoryPredicate;
import com.yqboots.web.thymeleaf.support.AbstractHtmlTreeResolver;
import com.yqboots.web.thymeleaf.support.HtmlTree;
import com.yqboots.web.thymeleaf.support.HtmlTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Resolves the available directories in the system, which apply permission controls.
 *
 * @author Eric H B Zhan
 * @since 1.2.0
 */
@Component
public class AvailableDirectoriesTreeResolver extends AbstractHtmlTreeResolver {
    private static final Logger LOG = LoggerFactory.getLogger(AvailableDirectoriesTreeResolver.class);

    private static final String NAME_KEY = "FSS_AVAILABLE_DIRS";

    private final FileItemManager fileItemManager;

    /**
     * Constructs {@link AvailableDirectoriesTreeResolver}
     */
    @Autowired
    public AvailableDirectoriesTreeResolver(final FileItemManager fileItemManager) {
        super(NAME_KEY);
        this.fileItemManager = fileItemManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HtmlTree getHtmlTree(final String name, final String... attributes) {
        final HtmlTree result = new HtmlTree();

        final Path root = fileItemManager.getRootPath();
        result.setId("/");
        result.setText(root.getFileName().toString());
        result.setNodes(getHtmlTreeNodes(root));

        return result;
    }

    private static List<HtmlTreeNode> getHtmlTreeNodes(final Path path) {
        final List<HtmlTreeNode> results = new ArrayList<>();
        try {
            Files.list(path).filter(new DirectoryPredicate()).forEach(new DirectoryConsumer(results, path));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return results;
    }

    private static class DirectoryConsumer implements Consumer<Path> {
        private final List<HtmlTreeNode> nodes;
        private final Path root;

        public DirectoryConsumer(final List<HtmlTreeNode> nodes, final Path root) {
            this.nodes = nodes;
            this.root = root;
        }

        @Override
        public void accept(final Path path) {
            final HtmlTreeNode node = new HtmlTreeNode();

            final String subStr = StringUtils.substringAfter(path.toString(), root.toString());
            node.setId(StringUtils.replace(subStr, "\\", "/"));
            node.setText(path.getFileName().toString());
            node.setChildren(getHtmlTreeNodes(path));
            nodes.add(node);
        }
    }
}
