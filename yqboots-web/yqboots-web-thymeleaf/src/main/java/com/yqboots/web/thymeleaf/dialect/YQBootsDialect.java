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
package com.yqboots.web.thymeleaf.dialect;

import com.yqboots.web.thymeleaf.processor.attr.PageSummaryAttrProcessor;
import com.yqboots.web.thymeleaf.processor.element.*;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * The custom dialect.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class YQBootsDialect extends AbstractDialect {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefix() {
        return "yq";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new MenuElementProcessor());
        processors.add(new PageSummaryAttrProcessor());
        processors.add(new PaginationElementProcessor());
        processors.add(new OptionsElementProcessor());
        processors.add(new BreadcrumbsElementProcessor());
        processors.add(new AlertElementProcessor());
        return processors;
    }
}
