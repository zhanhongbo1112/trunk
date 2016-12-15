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
 * Tree node in the {@link HtmlTree}.
 *
 * @author Eric H B Zhan
 * @since 1.1.1
 */
public class HtmlTreeNode implements Serializable {
    private String id;

    private String text;

    private boolean leaf = false;

    private List<HtmlTreeNode> children;

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

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(final boolean leaf) {
        this.leaf = leaf;
    }

    public List<HtmlTreeNode> getChildren() {
        return children;
    }

    public void setChildren(final List<HtmlTreeNode> children) {
        this.children = children;
    }
}
