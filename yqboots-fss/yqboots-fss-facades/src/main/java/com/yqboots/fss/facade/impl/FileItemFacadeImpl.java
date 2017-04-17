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
package com.yqboots.fss.facade.impl;

import com.yqboots.fss.core.FileItem;
import com.yqboots.fss.facade.FileItemFacade;
import com.yqboots.fss.service.FileItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The service implementation that manages the FileItem..
 *
 * @author Eric H B Zhan
 * @since 1.4.0
 */
@Component
public class FileItemFacadeImpl implements FileItemFacade {
    @Autowired
    private FileItemService fileItemService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<FileItem> findByPath(final String path, final Pageable pageable) throws IOException {
        return fileItemService.findByPath(path, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String path) throws IOException {
        fileItemService.delete(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFullPath(final String relativePath) {
        return fileItemService.getFullPath(relativePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getRootPath() {
        return fileItemService.getRootPath();
    }
}
