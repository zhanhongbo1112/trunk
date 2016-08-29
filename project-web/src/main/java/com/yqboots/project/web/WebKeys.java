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
package com.yqboots.project.web;

/**
 * The common keys used in the controller class, to unify the key usage.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class WebKeys {
    /**
     * Used in the form for searching
     */
    public static final String SEARCH_FORM = "searchForm";

    /**
     * Used in the form for domain creation and update.
     */
    public static final String MODEL = "model";

    /**
     * Used in the list of paged data.
     */
    public static final String PAGE = "page";

    /**
     * Used in the parameters for the id of domain.
     */
    public static final String ID = "id";

    /**
     * Used in the parameter for creating new domain.
     */
    public static final String ACTION_NEW = "action=new";

    /**
     * Used in the parameter for updating existing domain.
     */
    public static final String ACTION_UPDATE = "action=update";

    /**
     * Used in the parameter for deleting existing domain.
     */
    public static final String ACTION_DELETE = "action=delete";
}
