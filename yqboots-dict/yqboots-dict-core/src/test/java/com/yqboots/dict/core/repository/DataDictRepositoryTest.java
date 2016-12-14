package com.yqboots.dict.core.repository;

import com.yqboots.dict.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(value = {Application.class})
public class DataDictRepositoryTest {

    @Autowired
    private DataDictRepository dataDictRepository;

    @Test
    public void testFindAllDataDictNames() throws Exception {
        final List<String> results = dataDictRepository.findAllDataDictNames();
        assertTrue(!results.isEmpty());
    }
}