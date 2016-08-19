package com.yqboots.project.dict.context;

import com.yqboots.project.dict.autoconfigure.DataDictProperties;
import com.yqboots.project.dict.core.DataDictManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * Created by Administrator on 2016-08-17.
 */
@Component
public class DataDictImportListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(DataDictImportListener.class);

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getApplicationContext();

        final DataDictManager manager = context.getBean(DataDictManager.class);
        final DataDictProperties properties = context.getBean(DataDictProperties.class);
        if (!properties.isImportEnabled()) {
            return;
        }

        final String location = properties.getImportFileLocation();
        if (StringUtils.isEmpty(location)) {
            LOG.warn("Data Dict Importing is enabled, but location was not set");
            return;
        }

        try {
            final File file = ResourceUtils.getFile(location);
            try (final InputStream inputStream = new FileInputStream(file)) {
                manager.imports(inputStream);
            }
        } catch (IOException e) {
            LOG.error("Failed to import Data Dicts", e);
        }
    }
}
