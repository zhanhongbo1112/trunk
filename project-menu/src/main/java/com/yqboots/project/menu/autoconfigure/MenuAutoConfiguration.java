package com.yqboots.project.menu.autoconfigure;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItems;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Created by Administrator on 2016-08-12.
 */
@Configuration
public class MenuAutoConfiguration {
    @Bean(name = "menuItemXmlMarshaller")
    public Jaxb2Marshaller menuItemXmlMarshaller() {
        Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(MenuItems.class, MenuItem.class);

        return bean;
    }
}
