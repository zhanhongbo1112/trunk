package com.yqboots.project.dict.web.controller;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.repository.DataDictRepository;
import com.yqboots.project.dict.web.form.DataDictSearchForm;
import com.yqboots.project.web.WebKeys;
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
@RequestMapping(value = "/project/dict")
@SessionAttributes(names = {WebKeys.SEARCH_FORM}, types = {DataDictSearchForm.class})
public class DataDictController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/dict";
    private static final String VIEW_HOME = "project/dict/index";
    private static final String VIEW_FORM = "project/dict/form";

    @Autowired
    private DataDictRepository dataDictRepository;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected DataDictSearchForm searchForm() {
        return new DataDictSearchForm();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final DataDictSearchForm searchForm,
                       @PageableDefault final Pageable pageable,
                       final ModelMap model) {
        String searchStr = StringUtils.defaultIfEmpty(searchForm.getName(), StringUtils.EMPTY);
        searchStr = StringUtils.trim(searchStr);
        model.addAttribute(WebKeys.PAGE, this.dataDictRepository.findByNameLikeIgnoreCaseOrderByName("%" + searchStr + "%",
                pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new DataDict());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, this.dataDictRepository.findOne(id));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final DataDict dict,
                         final BindingResult bindingResult,
                         final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        this.dataDictRepository.save(dict);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        this.dataDictRepository.delete(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
