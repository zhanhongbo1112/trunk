package com.yqboots.project.menu.autoconfigure;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItemManager;
import com.yqboots.project.menu.core.MenuItemManagerImpl;
import com.yqboots.project.menu.core.MenuItems;
import com.yqboots.project.menu.core.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by Administrator on 2016-08-12.
 */
@Configuration
@EnableConfigurationProperties(MenuItemProperties.class)
public class MenuItemAutoConfiguration {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemProperties properties;

    @Bean
    public MenuItemManager menuItemManager() {
        return new MenuItemManagerImpl(menuItemRepository, properties);
    }

    @Bean(name = "menuItemXmlMarshaller")
    public Jaxb2Marshaller menuItemXmlMarshaller() {
        Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(MenuItems.class, MenuItem.class);

        return bean;
    }
}
