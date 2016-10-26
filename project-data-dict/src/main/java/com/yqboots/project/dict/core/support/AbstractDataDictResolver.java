/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.project.dict.core.support;

import org.apache.commons.lang3.StringUtils;

/**
 * The default implementation that resolves DataDict by name.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public abstract class AbstractDataDictResolver implements DataDictResolver {
    /**
     * The name key.
     */
    private final String name;

    /**
     * Constructs <code>AbstractDataDictResolver</code>
     *
     * @param name name
     */
    public AbstractDataDictResolver(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final String name) {
        return StringUtils.equals(getName(), name);
    }

    /**
     * Gets the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }
}
