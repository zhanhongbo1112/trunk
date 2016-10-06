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
package com.yqboots.project.fss.web.controller;

import com.yqboots.project.fss.core.FileItem;
import com.yqboots.project.fss.core.FileItemManager;
import com.yqboots.project.fss.web.form.FileUploadForm;
import com.yqboots.project.fss.web.form.FileUploadFormValidator;
import com.yqboots.project.fss.web.util.FssWebUtils;
import com.yqboots.project.web.support.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller for FileItem.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/project/fss")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class FileItemController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/fss";
    private static final String VIEW_HOME = "project/fss/index";

    private static final String MODEL_DIRECTORIES = "directories";

    @Autowired
    private FileItemManager fileItemManager;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @ModelAttribute(WebKeys.FILE_UPLOAD_FORM)
    protected FileUploadForm fileUploadForm() {
        return new FileUploadForm();
    }

    @ModelAttribute(MODEL_DIRECTORIES)
    protected List<String> directories() {
        return fileItemManager.getAvailableDirectories();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) throws IOException {
        if (StringUtils.isBlank(searchForm.getCriterion())) {
            model.addAttribute(WebKeys.PAGE, new PageImpl<FileItem>(new ArrayList<>(), pageable, 0));
            return VIEW_HOME;
        }

        model.addAttribute(WebKeys.PAGE, fileItemManager.findByPath(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @RequestMapping(value = WebKeys.MAPPING_UPLOAD, method = RequestMethod.POST)
    public String upload(@Valid @ModelAttribute(WebKeys.FILE_UPLOAD_FORM) final FileUploadForm fileUploadForm,
                         @PageableDefault final Pageable pageable,
                         final BindingResult bindingResult,
                         final ModelMap model) throws IOException {
        new FileUploadFormValidator().validate(fileUploadForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(WebKeys.PAGE, fileItemManager.findByPath(StringUtils.EMPTY, pageable));
            return VIEW_HOME;
        }

        final MultipartFile file = fileUploadForm.getFile();
        final String path = fileUploadForm.getPath();
        final boolean overrideExisting = fileUploadForm.isOverrideExisting();

        final Path destination = Paths.get(fileItemManager.getFullPath(path) + File.separator + file.getOriginalFilename());
        if (Files.exists(destination) && overrideExisting) {
            file.transferTo(destination.toFile());
        } else {
            Files.createDirectories(destination.getParent());
            Files.createFile(destination);
            file.transferTo(destination.toFile());
        }

        model.clear();
        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DOWNLOAD}, method = RequestMethod.GET)
    public HttpEntity<byte[]> download(@RequestParam(WebKeys.ID) final String path) throws IOException {
        return FssWebUtils.downloadFile(fileItemManager.getFullPath(path), MediaType.APPLICATION_OCTET_STREAM);
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam(WebKeys.ID) final String path, final ModelMap model) throws IOException {
        fileItemManager.delete(path);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
