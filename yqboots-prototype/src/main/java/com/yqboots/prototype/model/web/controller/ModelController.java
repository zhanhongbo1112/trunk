package com.yqboots.prototype.model.web.controller;

import com.yqboots.prototype.model.core.ModelContext;
import com.yqboots.prototype.model.core.ProjectModeler;
import com.yqboots.prototype.model.web.ModelKeys;
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
@RequestMapping(value = ModelKeys.BASE_URL)
public class ModelController {
    @Autowired
    private ProjectModeler modeler;

    @ModelAttribute("context")
    public ModelContext modelContext() {
        return new ModelContext();
    }

    @RequestMapping
    public String index() {
        return ModelKeys.HOME_URL;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startup(final ModelContext context, final BindingResult bindingResult, final ModelMap model)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return ModelKeys.BASE_URL;
        }

        modeler.startup(context);

        model.clear();
        return "redirect:" + ModelKeys.BASE_URL;
    }
}
