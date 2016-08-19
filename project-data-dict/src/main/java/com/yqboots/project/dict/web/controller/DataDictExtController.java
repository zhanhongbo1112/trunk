package com.yqboots.project.dict.web.controller;

import com.yqboots.fss.web.util.FssWebUtils;
import com.yqboots.project.dict.core.DataDictManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping(value = "/project/dict")
public class DataDictExtController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/dict";
    private static final String VIEW_IMPORT = "project/dict/import";

    @Autowired
    @Qualifier("dataDictManager")
    private DataDictManager dataDictManager;

    @RequestMapping(value = "/imports", method = RequestMethod.GET)
    public String preImport() {
        return VIEW_IMPORT;
    }

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public String imports(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.isTrue(!file.isEmpty(), "the file should not be empty");

        try (InputStream inputStream = file.getInputStream()) {
            dataDictManager.imports(inputStream);
        }

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(value = "/exports", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        Path path = dataDictManager.exports();

        return FssWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
