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
package com.yqboots.project.security.web.controller;

import com.yqboots.project.security.core.Role;
import com.yqboots.project.security.core.RoleManager;
import com.yqboots.project.security.web.form.RoleForm;
import com.yqboots.project.security.web.form.RoleFormConverter;
import com.yqboots.project.web.form.SearchForm;
import com.yqboots.project.web.support.WebKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for {@link com.yqboots.project.security.core.Role}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Controller
@RequestMapping(value = "/project/security/role")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class RoleController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/security/role";
    private static final String VIEW_HOME = "project/security/role/index";
    private static final String VIEW_FORM = "project/security/role/form";

    @Autowired
    private RoleManager roleManager;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, roleManager.findRoles(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new RoleForm());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        Role role = roleManager.findRole(id);

        model.addAttribute(WebKeys.MODEL, new RoleFormConverter().convert(role));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final RoleForm domain,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        // TODO: exception handling
        if (!domain.isExisted()) {
            roleManager.addRole(domain.getPath(), domain.getUsers(), domain.getGroups());
        } else {
            roleManager.updateRole(domain.getPath(), domain.getUsers(), domain.getGroups());
        }

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        roleManager.removeRole(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
