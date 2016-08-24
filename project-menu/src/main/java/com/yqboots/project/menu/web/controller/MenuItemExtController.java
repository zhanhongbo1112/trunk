package com.yqboots.project.menu.web.controller;

import com.yqboots.project.fss.web.util.FssWebUtils;
import com.yqboots.project.menu.core.MenuItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-08-02.
 */
@Controller
@RequestMapping(value = "/project/menu")
public class MenuItemExtController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/menu";

    @Autowired
    private MenuItemManager menuItemManager;

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public String imports(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.isTrue(!file.isEmpty(), "the file should not be empty");

        try (InputStream inputStream = file.getInputStream()) {
            menuItemManager.imports(inputStream);
        }

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(value = "/exports", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        Path path = menuItemManager.exports();

        return FssWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
