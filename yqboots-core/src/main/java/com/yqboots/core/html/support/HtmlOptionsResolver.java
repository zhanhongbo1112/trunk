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
package com.yqboots.core.html.support;

import com.yqboots.core.html.HtmlOption;

import java.util.List;

/**
 * Resolve html options from other entities or objects.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public interface HtmlOptionsResolver {
    /**
     * Checks if the resolver supports the name key.
     *
     * @param name name
     * @return true if supports
     */
    boolean supports(String name);

    /**
     * Gets {@link HtmlOption}s.
     *
     * @param name       name
     * @param attributes attributes
     * @return list of HtmlOption
     */
    List<HtmlOption> getHtmlOptions(String name, String... attributes);
}
