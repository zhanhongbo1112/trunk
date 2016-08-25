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
package com.yqboots.project.fss.autoconfigure;

import com.yqboots.project.fss.core.FileItemManager;
import com.yqboots.project.fss.core.FileItemManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016-05-18.
 */
@Configuration
@EnableConfigurationProperties({FssProperties.class})
public class FssAutoConfiguration {
    @Autowired
    private FssProperties properties;

    @Bean
    @ConditionalOnMissingBean({FileItemManager.class})
    public FileItemManager fileItemRepository() {
        return new FileItemManagerImpl(properties);
    }
}
