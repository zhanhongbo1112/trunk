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
public class RoleToDataDictConsumer implements Consumer<Role> {
    /**
     * The key of the data dictionary.
     */
    private final String name;

    /**
     * The container of the data dictionaries.
     */
    private final List<DataDict> dataDicts;

    /**
     * Constructs the <code>RoleToDataDictConsumer</code>
     *
     * @param name      name key
     * @param dataDicts dataDicts
     */
    public RoleToDataDictConsumer(final String name, final List<DataDict> dataDicts) {
        this.name = name;
        this.dataDicts = dataDicts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Role role) {
        DataDict dataDict = new DataDict();
        dataDict.setName(getName());
        dataDict.setText(role.getPath() + " - " + role.getAlias());
        dataDict.setValue(Long.toString(role.getId()));
        dataDict.setDescription(role.getDescription());
        dataDicts.add(dataDict);
    }

    /**
     * Gets the name key.
     *
     * @return the key of the data dictionary
     */
    public String getName() {
        return name;
    }
}
