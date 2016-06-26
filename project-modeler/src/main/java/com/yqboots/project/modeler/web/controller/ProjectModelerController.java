package com.yqboots.project.modeler.web.controller;

import com.yqboots.project.modeler.core.ModelContext;
import com.yqboots.project.modeler.core.ProjectModeler;
import com.yqboots.project.modeler.web.ProjectModelerKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by Administrator on 2016-06-09.
 */
@Controller
@RequestMapping(value = ProjectModelerKeys.BASE_URL)
public class ProjectModelerController {
    @Autowired
    private ProjectModeler modeler;

    @ModelAttribute("context")
    public ModelContext modelContext() {
        return new ModelContext();
    }

    @RequestMapping
    public String index() {
        return ProjectModelerKeys.HOME_URL;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startup(final ModelContext context, final BindingResult bindingResult, final ModelMap model)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return ProjectModelerKeys.BASE_URL;
        }

        modeler.startup(context);

        model.clear();
        return "redirect:" + ProjectModelerKeys.BASE_URL;
    }
}
