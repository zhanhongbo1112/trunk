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
package com.yqboots.project.dict.web.controller;

import com.yqboots.project.dict.core.DataDictManager;
import com.yqboots.project.fss.web.util.FssWebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * The extension Controller for DataDict, providing Data Dictionary importing and exporting functions, etc.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/project/dict")
public class DataDictExtController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/dict";

    @Autowired
    @Qualifier("dataDictManager")
    private DataDictManager dataDictManager;

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public String imports(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.isTrue(!file.isEmpty(), "the file should not be empty");

        try (InputStream inputStream = file.getInputStream()) {
            dataDictManager.imports(inputStream);
        }

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(value = "/exports", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        Path path = dataDictManager.exports();

        return FssWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
