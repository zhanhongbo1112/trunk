package com.yqboots.project.fss.web.controller;

import com.yqboots.project.fss.core.FileItem;
import com.yqboots.project.fss.core.repository.FileItemRepository;
import com.yqboots.project.web.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2016-08-02.
 */
@Controller
@RequestMapping(value = "/project/fss")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class FileItemController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/fss";
    private static final String VIEW_HOME = "project/fss/index";

    @Autowired
    private FileItemRepository fileItemRepository;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable, final ModelMap model) {
        String searchStr = StringUtils.defaultIfEmpty(searchForm.getCriterion(), StringUtils.EMPTY);
        searchStr = StringUtils.trim(searchStr);
        Page<FileItem> pagedData = fileItemRepository.findByPath("%" + searchStr + "%", pageable);
        model.addAttribute(WebKeys.PAGE, pagedData);
        return VIEW_HOME;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final String path, final ModelMap model) {
        this.fileItemRepository.delete(path);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
