/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.dict.core;

import com.yqboots.core.util.DBUtils;
import com.yqboots.dict.autoconfigure.DataDictProperties;
import com.yqboots.dict.core.repository.DataDictRepository;
import com.yqboots.fss.core.support.FileType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Manages the Data Dictionary the project has.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Transactional(readOnly = true)
public class DataDictManagerImpl implements DataDictManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDictManagerImpl.class);

    private DataDictRepository dataDictRepository;

    private DataDictProperties properties;

    /**
     * For marshalling and unmarshalling Data Dictionaries.
     */
    private static Jaxb2Marshaller jaxb2Marshaller;

    static {
        jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(DataDicts.class, DataDict.class);
    }

    /**
     * {@inheritDoc}
     */
    public DataDictManagerImpl(final DataDictRepository dataDictRepository, final DataDictProperties properties) {
        this.dataDictRepository = dataDictRepository;
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDict getDataDict(final Long id) {
        return dataDictRepository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DataDict> getDataDicts(final String wildcardName, final Pageable pageable) {
        String searchStr = StringUtils.trim(StringUtils.defaultString(wildcardName));
        return dataDictRepository.findByNameLikeIgnoreCase(DBUtils.wildcard(searchStr), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDict> getDataDicts(final String name) {
        Assert.hasText(name, "name is required");

        return dataDictRepository.findByNameOrderByText(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final String name, final String value) {
        return getText(name, value, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final String name, final String value, boolean valueIncluded) {
        // TODO: add cache
        final List<DataDict> all = getDataDicts(name);

        final DataDict item = (DataDict) CollectionUtils.find(all, o -> {
            DataDict dict = (DataDict) o;
            return StringUtils.equals(dict.getValue(), value);
        });

        if (item == null) {
            LOGGER.warn("No data dict for {} with value [{}]", name, value);
            return value;
        }

        return valueIncluded ? StringUtils.join(new String[]{item.getValue(), item.getText()}, " - ") : item.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(final DataDict entity) throws DataDictExistsException {
        if (!entity.isNew()) {
            dataDictRepository.save(entity);
            return;
        }

        Assert.hasText(entity.getName(), "name is required");
        DataDict existed = dataDictRepository.findByNameAndValue(entity.getName(), entity.getValue());
        if (existed != null) {
            throw new DataDictExistsException("The DataDict has already existed");
        }

        dataDictRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(final Long id) {
        if (dataDictRepository.exists(id)) {
            this.dataDictRepository.delete(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void imports(final InputStream inputStream) throws IOException {
        final DataDicts dataDicts = (DataDicts) jaxb2Marshaller.unmarshal(new StreamSource(inputStream));
        for (DataDict dict : dataDicts.getDataDicts()) {
            LOGGER.debug("importing data dict with name \"{}\", text \"{}\" and value \"{}\"",
                    dict.getName(), dict.getText(), dict.getValue());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Path exports() throws IOException {
        final String fileName = properties.getExportFileNamePrefix() + LocalDate.now() + FileType.DOT_XML;

        final Path exportFileLocation = properties.getExportFileLocation();
        if (!Files.exists(exportFileLocation)) {
            Files.createDirectories(exportFileLocation);
        }

        final Path result = Paths.get(exportFileLocation + File.separator + fileName);
        try (FileWriter writer = new FileWriter(result.toFile())) {
            final List<DataDict> dataDicts = dataDictRepository.findAll();
            jaxb2Marshaller.marshal(new DataDicts(dataDicts), new StreamResult(writer));
        }

        return result;
    }
}
