package com.yqboots.project.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for login.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/project/security")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "project/security/login";
    }
}
