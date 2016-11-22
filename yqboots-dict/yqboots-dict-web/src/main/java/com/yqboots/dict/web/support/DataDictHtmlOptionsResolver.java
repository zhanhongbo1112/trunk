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
package com.yqboots.dict.web.support;

import com.yqboots.core.html.HtmlOption;
import com.yqboots.core.html.support.AbstractHtmlOptionsResolver;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDictManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Resolves html option from data dictionaries.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Component
@Order // be the last one
public class DataDictHtmlOptionsResolver extends AbstractHtmlOptionsResolver {
    private final DataDictManager dataDictManager;

    @Autowired
    public DataDictHtmlOptionsResolver(final DataDictManager dataDictManager) {
        super();
        this.dataDictManager = dataDictManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final String name) {
        // this resolver is the last one, always check if exists in the data dict.
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HtmlOption> getHtmlOptions(final String name, final String... attributes) {
        List<HtmlOption> results = new ArrayList<>();

        List<DataDict> dataDicts = dataDictManager.getDataDicts(name);
        dataDicts.forEach(new DataDictConsumer(results));

        return results;
    }

    private static class DataDictConsumer implements Consumer<DataDict> {
        /**
         * The container of the data options.
         */
        private final List<HtmlOption> options;

        public DataDictConsumer(final List<HtmlOption> options) {
            this.options = options;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void accept(final DataDict dataDict) {
            HtmlOption options = new HtmlOption();
            options.setText(dataDict.getText());
            options.setValue(dataDict.getValue());
            this.options.add(options);
        }
    }
}
