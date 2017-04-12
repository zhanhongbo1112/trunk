/*
 * Copyright 2015-2017 the original author or authors.
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
package com.yqboots.dict.context;

import com.yqboots.dict.DataDictConstants;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDicts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * The configuration for {@code com.yqboots.dict.core.DataDict}.
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Configuration
public class DataDictConfiguration {
    @Lazy
    @Bean(name = DataDictConstants.BEAN_JAXB2_MARSHALLER)
    public Jaxb2Marshaller dataDictJaxb2Marshaller() {
        final Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(DataDicts.class, DataDict.class);

        return bean;
    }
}
