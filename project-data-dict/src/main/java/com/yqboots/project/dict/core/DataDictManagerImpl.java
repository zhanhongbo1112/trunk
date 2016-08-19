package com.yqboots.project.dict.core;

import com.yqboots.fss.core.support.FileType;
import com.yqboots.project.dict.autoconfigure.DataDictProperties;
import com.yqboots.project.dict.core.repository.DataDictRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
@Transactional(readOnly = true)
public class DataDictManagerImpl implements DataDictManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDictManagerImpl.class);

    private DataDictRepository dataDictRepository;

    private DataDictProperties properties;

    private static Jaxb2Marshaller jaxb2Marshaller;

    static {
        jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(DataDicts.class, DataDict.class);
    }

    @Autowired
    public DataDictManagerImpl(final DataDictRepository dataDictRepository, final DataDictProperties properties) {
        this.dataDictRepository = dataDictRepository;
        this.properties = properties;
    }

    @Override
    public List<DataDict> getDataDicts(final String name) {
        return dataDictRepository.findByNameOrderByText(name);
    }

    @Override
    public String getText(final String name, final String value) {
        return getText(name, value, false);
    }

    @Override
    public String getText(final String name, final String value, boolean valueIncluded) {
        // TODO: add cache
        List<DataDict> all = getDataDicts(name);

        DataDict item = (DataDict) CollectionUtils.find(all, o -> {
            DataDict dict = (DataDict) o;
            return StringUtils.equals(dict.getValue(), value);
        });

        if (item == null) {
            LOGGER.warn("No data dict for {} with value [{}]", name, value);
            return value;
        }

        String result = item.getText();
        if (valueIncluded) {
            result = StringUtils.join(new String[]{item.getValue(), item.getText()}, " - ");
        }

        return result;
    }

    @Override
    @Transactional
    public void imports(final InputStream inputStream) throws IOException {
        DataDicts dataDicts = (DataDicts) jaxb2Marshaller.unmarshal(new StreamSource(inputStream));
        for (DataDict dict : dataDicts.getDataDicts()) {
            DataDict existOne = dataDictRepository.findByNameAndValue(dict.getName(), dict.getValue());
            if (existOne == null) {
                dataDictRepository.save(dict);
                continue;
            }

            existOne.setText(dict.getText());
            existOne.setDescription(dict.getDescription());
            dataDictRepository.save(existOne);
        }
    }

    @Override
    public Path exports() throws IOException {
        final String fileName = properties.getExportFileNamePrefix() + LocalDate.now() + FileType.DOT_XML;
        final Path result = Paths.get(properties.getExportFileLocation() + File.separator + fileName);

        final List<DataDict> dataDicts = dataDictRepository.findAll();

        FileWriter writer = new FileWriter(result.toFile());
        jaxb2Marshaller.marshal(new DataDicts(dataDicts), new StreamResult(writer));

        return result;
    }
}
