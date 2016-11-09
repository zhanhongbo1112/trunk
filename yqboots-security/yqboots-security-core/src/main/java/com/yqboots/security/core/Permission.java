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
package com.yqboots.security.core;

import java.io.Serializable;

/**
 * Permission entity.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class Permission implements Serializable {
    private String sid;

    private long path;

    private String clazz;

    private int mask;

    public String getSid() {
        return sid;
    }

    public void setSid(final String sid) {
        this.sid = sid;
    }

    public long getPath() {
        return path;
    }

    public void setPath(final long path) {
        this.path = path;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(final String clazz) {
        this.clazz = clazz;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(final int mask) {
        this.mask = mask;
    }
}
