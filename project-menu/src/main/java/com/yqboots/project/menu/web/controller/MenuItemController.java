package com.yqboots.project.menu.web.controller;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.repository.MenuItemRepository;
import com.yqboots.project.web.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Administrator on 2016-08-02.
 */
@Controller
@RequestMapping(value = "/project/menu")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class MenuItemController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/menu";
    private static final String VIEW_HOME = "project/menu/index";
    private static final String VIEW_FORM = "project/menu/form";

    @Autowired
    private MenuItemRepository menuItemRepository;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        String searchStr = StringUtils.defaultIfEmpty(searchForm.getCriterion(), StringUtils.EMPTY);
        searchStr = StringUtils.trim(searchStr);
        model.addAttribute(WebKeys.PAGE, this.menuItemRepository.findByNameLikeIgnoreCaseOrderByName("%" + searchStr + "%",
                pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new MenuItem());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, this.menuItemRepository.findOne(id));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final MenuItem menuItem,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        this.menuItemRepository.save(menuItem);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        this.menuItemRepository.delete(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
