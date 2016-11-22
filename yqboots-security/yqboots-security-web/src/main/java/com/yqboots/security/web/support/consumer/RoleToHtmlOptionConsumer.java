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
package com.yqboots.security.web.support.consumer;

import com.yqboots.core.html.HtmlOption;
import com.yqboots.dict.core.DataDict;
import com.yqboots.security.core.Role;

import java.util.List;
import java.util.function.Consumer;

/**
 * Consumes {@link com.yqboots.security.core.Role} and put into {@link DataDict}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class RoleToHtmlOptionConsumer implements Consumer<Role> {
    /**
     * The key of the data dictionary.
     */
    private final String name;

    /**
     * The container of the data options.
     */
    private final List<HtmlOption> options;

    /**
     * Constructs the {@link RoleToHtmlOptionConsumer}.
     *
     * @param name    name key
     * @param options options
     */
    public RoleToHtmlOptionConsumer(final String name, final List<HtmlOption> options) {
        this.name = name;
        this.options = options;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Role role) {
        HtmlOption options = new HtmlOption();
        options.setText(role.getPath() + " - " + role.getAlias());
        options.setValue(Long.toString(role.getId()));
        this.options.add(options);
    }

    /**
     * Gets the name key.
     *
     * @return the key of the data option
     */
    public String getName() {
        return name;
    }
}
