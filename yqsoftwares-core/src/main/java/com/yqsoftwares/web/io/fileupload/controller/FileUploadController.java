package com.yqsoftwares.web.io.fileupload.controller;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Administrator on 2015-12-14.
 */
@RestController
@RequestMapping(value = "/web/io/file/upload", method = RequestMethod.POST)
public class FileUploadController {
    @RequestMapping(value = "/single")
    public String upload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file)
            throws IOException {
        Assert.isTrue(!file.isEmpty(), "the file should not be empty");

        String storedName = file.getOriginalFilename();
        if (StringUtils.hasText(name)) {
            storedName = name;
        }

        String result = "You successfully uploaded " + storedName + "!";

        OutputStream os = null;
        try {
            byte[] bytes = file.getBytes();
            os = new BufferedOutputStream(new FileOutputStream(new File(storedName)));
            os.write(bytes);
        } catch (Exception e) {
            result = "You failed to upload " + storedName + " => " + e.getMessage();
        } finally {
            os.close();
        }

        return result;
    }

    @RequestMapping(value = "/multiple")
    public String upload(@RequestParam("name") String[] names, @RequestParam("file") MultipartFile[] files)
            throws IOException {
        Assert.isTrue(files.length > 0, "the files should not be empty");

        String result = "";

        String fileName = null;

        OutputStream os = null;
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    fileName = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();
                    os = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                    os.write(bytes);

                    result += "You have successfully uploaded " + fileName + "<br/>";
                }
            }
        } catch (Exception e) {
            result = "You failed to upload " + fileName + " => " + e.getMessage() + "<br/>";
        } finally {
            os.close();
        }

        return result;
    }
}
