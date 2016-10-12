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
package com.yqboots.project.menu.web.controller;

import com.yqboots.project.fss.web.util.FssWebUtils;
import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItemExistsException;
import com.yqboots.project.menu.core.MenuItemManager;
import com.yqboots.project.menu.web.form.FileUploadForm;
import com.yqboots.project.menu.web.form.FileUploadFormValidator;
import com.yqboots.project.web.support.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.oxm.XmlMappingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * The controller for MenuItem.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/project/menu")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class MenuItemController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/menu";
    private static final String VIEW_HOME = "project/menu/index";
    private static final String VIEW_FORM = "project/menu/form";

    @Autowired
    private MenuItemManager menuItemManager;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @ModelAttribute(WebKeys.FILE_UPLOAD_FORM)
    protected FileUploadForm fileUploadForm() {
        return new FileUploadForm();
    }

    @PreAuthorize("hasPermission('/project/menu', 'R')")
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, menuItemManager.getMenuItems(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @PreAuthorize("hasPermission('/project/menu', 'W')")
    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new MenuItem());
        return VIEW_FORM;
    }

    @PreAuthorize("hasPermission('/project/menu', 'W')")
    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, menuItemManager.getMenuItem(id));
        return VIEW_FORM;
    }

    @PreAuthorize("hasPermission('/project/menu', 'W')")
    @RequestMapping(value = WebKeys.MAPPING_ROOT, method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final MenuItem menuItem,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        try {
            menuItemManager.update(menuItem);
        } catch (MenuItemExistsException e) {
            bindingResult.reject("I0001");
            return VIEW_FORM;
        }

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @PreAuthorize("hasPermission('/project/menu', 'D')")
    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        menuItemManager.delete(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @PreAuthorize("hasPermission('/project/menu', 'W')")
    @RequestMapping(value = WebKeys.MAPPING_IMPORTS, method = RequestMethod.POST)
    public String imports(@ModelAttribute(WebKeys.FILE_UPLOAD_FORM) FileUploadForm fileUploadForm,
                          @PageableDefault final Pageable pageable,
                          final BindingResult bindingResult,
                          final ModelMap model) throws IOException {
        new FileUploadFormValidator().validate(fileUploadForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(WebKeys.PAGE, menuItemManager.getMenuItems(StringUtils.EMPTY, pageable));
            return VIEW_HOME;
        }

        try (InputStream inputStream = fileUploadForm.getFile().getInputStream()) {
            menuItemManager.imports(inputStream);
        } catch (XmlMappingException e) {
            bindingResult.rejectValue(WebKeys.FILE, "I0003");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(WebKeys.PAGE, menuItemManager.getMenuItems(StringUtils.EMPTY, pageable));
            return VIEW_HOME;
        }

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @PreAuthorize("hasPermission('/project/menu', 'R')")
    @RequestMapping(value = WebKeys.MAPPING_EXPORTS, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        Path path = menuItemManager.exports();

        return FssWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
