package com.yqboots.prototype.project.web.controller;

import com.yqboots.prototype.project.core.ProjectContext;
import com.yqboots.prototype.project.core.ProjectInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016-06-04.
 */
@Controller
@RequestMapping(value = "/prototype/project")
public class ProjectController {
    @Autowired
    private ProjectInitializer initializer;

    @ModelAttribute("projectContext")
    public ProjectContext projectContext() {
        return new ProjectContext();
    }

    @RequestMapping
    public String index() {
        return "prototype/index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startup(final ProjectContext context, final BindingResult bindingResult, final ModelMap model)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return "/prototype/project";
        }

        initializer.startup(context);

        model.clear();
        return "redirect:/prototype/project";
    }
}
