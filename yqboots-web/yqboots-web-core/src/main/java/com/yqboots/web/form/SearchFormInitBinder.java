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
package com.yqboots.web.form;

import com.yqboots.web.support.WebKeys;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * The controller advice, which clear the "searchForm" attribute in the session, for the next request.
 * <p>If not, there will be an exception thrown when the search form key is the same in different controller</p>
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@ControllerAdvice
public class SearchFormInitBinder {
    /**
     * {@inheritDoc}
     */
    @InitBinder
    public void initBinder(HttpServletRequest request) {
        WebUtils.setSessionAttribute(request, WebKeys.SEARCH_FORM, null);
    }
}
