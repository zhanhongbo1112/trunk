package com.yqboots.web.thymeleaf.support;

import com.yqboots.web.thymeleaf.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.ObjectError;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class Html2PdfGeneratorTest {

    @Autowired
    private Html2PdfGenerator html2PdfGenerator;

    @Test
    public void testGenerate() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("text", "hello world");
        html2PdfGenerator.generate("test", variables, Paths.get("D://test.pdf"));
    }
}