package com.yqboots.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016-04-29.
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/profile")
    public String profile() {
        return "profile/index";
    }
}
