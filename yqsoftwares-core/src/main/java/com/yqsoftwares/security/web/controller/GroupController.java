package com.yqsoftwares.security.web.controller;

import com.yqsoftwares.security.core.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2015-12-14.
 */
@RestController
@RequestMapping(value = "/security/group")
public class GroupController {
    @Autowired
    private GroupManager groupManager;
}
