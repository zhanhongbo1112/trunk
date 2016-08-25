package com.yqboots.project.fss.web.controller;

import com.yqboots.project.fss.core.FileItem;
import com.yqboots.project.fss.core.FileItemManager;
import com.yqboots.project.fss.web.form.FileUploadForm;
import com.yqboots.project.web.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-08-02.
 */
@Controller
@RequestMapping(value = "/project/fss")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class FileItemController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/fss";
    private static final String VIEW_HOME = "project/fss/index";

    private static final String FILE_UPLOAD_FORM = "fileUploadForm";

    @Autowired
    private FileItemManager fileItemRepository;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @ModelAttribute(FILE_UPLOAD_FORM)
    protected FileUploadForm fileUploadForm() {
        return new FileUploadForm();
    }

    @ModelAttribute("directories")
    protected List<String> directories() {
        return fileItemRepository.getAvailableDirectories();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable, final ModelMap model) throws IOException {
        if (StringUtils.isBlank(searchForm.getCriterion())) {
            model.addAttribute(WebKeys.PAGE, new PageImpl<FileItem>(new ArrayList<>(), pageable, 0));
            return VIEW_HOME;
        }

        Page<FileItem> pagedData = fileItemRepository.findByPath(searchForm.getCriterion(), pageable);
        model.addAttribute(WebKeys.PAGE, pagedData);
        return VIEW_HOME;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@Valid @ModelAttribute(FILE_UPLOAD_FORM) final FileUploadForm form,
                         final BindingResult bindingResult, final ModelMap model) throws IOException {
        if (bindingResult.hasErrors()) {
            return VIEW_HOME;
        }

        final MultipartFile file = form.getFile();
        if (StringUtils.isBlank(file.getName())) {
            return VIEW_HOME;
        }

        Path destination = fileItemRepository.getFullPath(form.getPath());
        destination = Paths.get(destination + File.separator + file.getOriginalFilename());
        if (Files.exists(destination) && form.isOverrideExisting()) {
            file.transferTo(destination.toFile());
        } else {
            Files.createDirectories(destination.getParent());
            Files.createFile(destination);
            file.transferTo(destination.toFile());
        }

        model.clear();
        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam(WebKeys.ID) final String path, final ModelMap model) throws IOException {
        this.fileItemRepository.delete(path);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
