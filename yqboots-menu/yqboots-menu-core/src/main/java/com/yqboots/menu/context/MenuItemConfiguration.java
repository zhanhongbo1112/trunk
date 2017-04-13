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
package com.yqboots.menu.context;

import com.yqboots.menu.MenuItemConstants;
import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.MenuItems;
import com.yqboots.menu.security.access.MenuItemObjectIdentityRetrieval;
import com.yqboots.security.access.support.ObjectIdentityRetrieval;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * The auto configuration class for MenuItem.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
public class MenuItemConfiguration {
    @Lazy
    @Bean(name = MenuItemConstants.BEAN_JAXB2_MARSHALLER)
    public Jaxb2Marshaller menuItemJaxb2Marshaller() {
        Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(MenuItems.class, MenuItem.class);

        return bean;
    }

    @Configuration
    @ConditionalOnClass(ObjectIdentityRetrieval.class)
    protected static class MenuItemSecurityConfiguration {
        @Bean
        public ObjectIdentityRetrieval menuItemObjectIdentityRetrieval() {
            return new MenuItemObjectIdentityRetrieval();
        }
    }
}
