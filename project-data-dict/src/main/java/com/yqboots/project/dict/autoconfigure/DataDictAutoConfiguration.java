package com.yqboots.project.dict.autoconfigure;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDictManager;
import com.yqboots.project.dict.core.DataDictManagerImpl;
import com.yqboots.project.dict.core.DataDicts;
import com.yqboots.project.dict.core.repository.DataDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by Administrator on 2016-08-01.
 */
@Configuration
public class DataDictAutoConfiguration {
    @Autowired
    private DataDictRepository dataDictRepository;

    @Bean(name = {"dicts", "dataDictManager"})
    public DataDictManager dataDictManager() {
        return new DataDictManagerImpl(dataDictRepository);
    }

    @Bean(name = "dataDictXmlMarshaller")
    public Jaxb2Marshaller dataDictXmlMarshaller() {
        Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(DataDicts.class, DataDict.class);

        return bean;
    }
}
