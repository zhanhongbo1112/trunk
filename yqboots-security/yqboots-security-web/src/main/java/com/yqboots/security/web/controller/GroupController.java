package com.yqboots.security.web.controller;

import com.yqboots.security.core.Group;
import com.yqboots.security.core.GroupManager;
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

        // TODO: exception handling
        if (!domain.isExisted()) {
            groupManager.addGroup(domain.getPath(), domain.getUsers(), domain.getRoles());
        } else {
            groupManager.updateGroup(domain.getPath(), domain.getUsers(), domain.getRoles());
        }

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
