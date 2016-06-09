package com.yqboots.prototype.project.web.controller;

import com.yqboots.prototype.project.core.ProjectContext;
import com.yqboots.prototype.project.core.ProjectInitializer;
import com.yqboots.prototype.project.web.ProjectKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by Administrator on 2016-06-04.
 */
@Controller
@RequestMapping(value = ProjectKeys.BASE_URL)
public class ProjectController {
    @Autowired
    private ProjectInitializer initializer;

    @ModelAttribute("context")
    public ProjectContext projectContext() {
        return new ProjectContext();
    }

    @RequestMapping
    public String index() {
        return ProjectKeys.HOME_URL;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startup(final ProjectContext context, final BindingResult bindingResult, final ModelMap model)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return ProjectKeys.BASE_URL;
        }

        initializer.startup(context);

        model.clear();
        return "redirect:" + ProjectKeys.BASE_URL;
    }
}
