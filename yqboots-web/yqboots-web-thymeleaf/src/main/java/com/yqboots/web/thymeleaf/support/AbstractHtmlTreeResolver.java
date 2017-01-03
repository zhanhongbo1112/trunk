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

import org.apache.commons.lang3.StringUtils;

/**
 * The default implementation that resolves html tree by name.
 *
 * @author Eric H B Zhan
 * @since 1.2.0
 */
public abstract class AbstractHtmlTreeResolver implements HtmlTreeResolver {
    /**
     * The name key.
     */
    private String name;

    /**
     * Constructs {@link AbstractHtmlTreeResolver}
     *
     * @param name name
     */
    public AbstractHtmlTreeResolver(final String name) {
        super();
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final String name) {
        return StringUtils.equals(this.name, name);
    }
}
