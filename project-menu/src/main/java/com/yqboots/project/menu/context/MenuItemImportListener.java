package com.yqboots.project.menu.context;

import com.yqboots.project.menu.autoconfigure.MenuItemProperties;
import com.yqboots.project.menu.core.MenuItemManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016-08-17.
 */
@Component
public class MenuItemImportListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemImportListener.class);

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getApplicationContext();

        final MenuItemManager manager = context.getBean(MenuItemManager.class);
        final MenuItemProperties properties = context.getBean(MenuItemProperties.class);
        if (!properties.isImportEnabled()) {
            return;
        }

        final String location = properties.getImportFileLocation();
        if (StringUtils.isEmpty(location)) {
            LOG.warn("Menu Item Importing is enabled, but location was not set");
            return;
        }

        try {
            final File file = ResourceUtils.getFile(location);
            try (final InputStream inputStream = new FileInputStream(file)) {
                manager.imports(inputStream);
            }
        } catch (IOException e) {
            LOG.error("Failed to import Menu Items", e);
        }
    }
}
