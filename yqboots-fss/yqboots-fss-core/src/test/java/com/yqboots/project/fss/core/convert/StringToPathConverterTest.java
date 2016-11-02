package com.yqboots.project.fss.core.convert;

import com.yqboots.project.fss.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Path;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class StringToPathConverterTest {
    private StringToPathConverter converter = new StringToPathConverter();

    @Test
    public void testConvert() throws Exception {
        String strPath = "D:/store/temp";
        Path path = converter.convert(strPath);
        Assert.assertNotNull(path);
    }
}