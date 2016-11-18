package com.yqboots.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for basic functions.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/security/login", method = RequestMethod.GET)
    public String login() {
        return "security/login";
    }

    @RequestMapping(value = "/security/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "security/403";
    }

    @PreAuthorize("hasPermission('/', 'com.yqboots.menu.core.MenuItem', 'READ')")
    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @PreAuthorize("hasPermission('/projects/framework', 'com.yqboots.menu.core.MenuItem', 'READ')")
    @RequestMapping(value = "/projects/framework")
    public String framework() {
        return "projects/framework/index";
    }
}
