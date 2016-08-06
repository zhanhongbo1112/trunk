package com.yqboots.project.dict.core;

import com.yqboots.project.dict.core.repository.DataDictRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
@Transactional(readOnly = true)
public class DataDictManagerImpl implements DataDictManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataDictManagerImpl.class);

    private DataDictRepository dataDictRepository;

    @Autowired
    @Qualifier(value = "dataDictXmlMarshaller")
    private Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    public DataDictManagerImpl(final DataDictRepository dataDictRepository) {
        this.dataDictRepository = dataDictRepository;
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
}
