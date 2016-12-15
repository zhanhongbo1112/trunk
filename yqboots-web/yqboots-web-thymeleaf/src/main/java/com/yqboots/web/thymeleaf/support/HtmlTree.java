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
package com.yqboots.web.thymeleaf.support;

import java.io.Serializable;
import java.util.List;

/**
 * Tree in the html page.
 *
 * @author Eric H B Zhan
 * @since 1.1.1
 */
public class HtmlTree implements Serializable {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    private List<HtmlTreeNode> nodes;

    public List<HtmlTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(final List<HtmlTreeNode> nodes) {
        this.nodes = nodes;
    }
}
