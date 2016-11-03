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
package com.yqboots.security.web.controller;

import com.yqboots.security.core.Group;
import com.yqboots.security.core.GroupExistsException;
import com.yqboots.security.core.GroupManager;
import com.yqboots.security.core.GroupNotFoundException;
import com.yqboots.security.web.form.GroupForm;
import com.yqboots.security.web.form.GroupFormConverter;
import com.yqboots.web.form.SearchForm;
import com.yqboots.web.support.WebKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for {@link com.yqboots.security.core.Group}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Controller
@RequestMapping(value = "/security/group")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class GroupController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/security/group";
    private static final String VIEW_HOME = "security/group/index";
    private static final String VIEW_FORM = "security/group/form";

    @Autowired
    private GroupManager groupManager;

    @ExceptionHandler(value = {GroupExistsException.class, GroupNotFoundException.class})
    protected void handleException(Exception ex, BindingResult bindingResult) {
        bindingResult.reject(ex.getMessage());
    }

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, groupManager.findGroups(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new GroupForm());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        Group group = groupManager.findGroup(id);

        model.addAttribute(WebKeys.MODEL, new GroupFormConverter().convert(group));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final GroupForm domain,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        if (!domain.isExisted()) {
            Group group = new Group();
            group.setPath(domain.getPath());
            group.setAlias(domain.getAlias());
            group.setDescription(domain.getDescription());
            groupManager.addGroup(group);
        } else {
            Group group = groupManager.findGroup(domain.getPath());
            group.setAlias(domain.getAlias());
            group.setDescription(domain.getDescription());
            groupManager.updateGroup(group);
        }

        groupManager.updateUsers(domain.getPath(), domain.getUsers());
        groupManager.updateRoles(domain.getPath(), domain.getRoles());

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        groupManager.removeGroup(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
