package com.yqboots.fss.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ZipUtilsTest {

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists(Paths.get("D:\\temp.zip"));
        Files.createDirectories(Paths.get("D:\\temp\\test"));
        Files.createFile(Paths.get("D:\\temp\\test\\temp.txt"));
    }

    @Test
    public void testCompress() throws Exception {
        ZipUtils.compress(Paths.get("D:\\temp"));
    }

    @After
    public void destroy() throws Exception {
        Files.deleteIfExists(Paths.get("D:\\temp\\test\\temp.txt"));
        Files.deleteIfExists(Paths.get("D:\\temp\\test"));
        Files.deleteIfExists(Paths.get("D:\\temp"));
    }
}