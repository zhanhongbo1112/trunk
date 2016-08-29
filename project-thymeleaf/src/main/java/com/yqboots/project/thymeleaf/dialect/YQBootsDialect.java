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
package com.yqboots.project.thymeleaf.dialect;

import com.yqboots.project.thymeleaf.processor.attr.PageSummaryAttrProcessor;
import com.yqboots.project.thymeleaf.processor.element.BreadcrumbsElementProcessor;
import com.yqboots.project.thymeleaf.processor.element.MenuElementProcessor;
import com.yqboots.project.thymeleaf.processor.element.OptionsElementProcessor;
import com.yqboots.project.thymeleaf.processor.element.PaginationElementProcessor;
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
     * <p>
     * Returns the default dialect prefix (the one that will be used if none is explicitly
     * specified during dialect configuration).
     * </p>
     * <p>
     * If <tt>null</tt> is returned, then every attribute
     * and/or element is considered processable by the processors in the dialect that apply
     * to that kind of node (elements with their attributes), and not only those that start
     * with a specific prefix.
     * </p>
     * <p>
     * Prefixes are <b>not</b> exclusive to a dialect: several dialects can declare the same
     * prefix, effectively acting as an aggregate dialect.
     * </p>
     *
     * @return the dialect prefix.
     */
    @Override
    public String getPrefix() {
        return "yq";
    }

    /**
     * Gets the processors for the dialect.
     *
     * @return list of processors
     */
    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new MenuElementProcessor());
        processors.add(new PageSummaryAttrProcessor());
        processors.add(new PaginationElementProcessor());
        processors.add(new OptionsElementProcessor());
        processors.add(new BreadcrumbsElementProcessor());
        return processors;
    }
}
