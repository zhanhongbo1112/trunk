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
package com.yqboots.security.web.model;

import java.io.Serializable;

/**
 * To represent the one-to-many relationship.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@SuppressWarnings("serial")
public class OneToManyHolder<O extends Serializable, M extends Serializable> implements Serializable {
    O one;
    M[] many;

    public OneToManyHolder() {
        super();
    }

    public OneToManyHolder(O one, M[] many) {
        super();
        this.one = one;
        this.many = many;
    }

    public O getOne() {
        return one;
    }

    public void setOne(O one) {
        this.one = one;
    }

    public M[] getMany() {
        return many;
    }

    public void setMany(M[] many) {
        this.many = many;
    }
}
