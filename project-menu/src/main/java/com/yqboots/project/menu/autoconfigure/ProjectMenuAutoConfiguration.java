package com.yqboots.project.menu.autoconfigure;

import com.yqboots.project.menu.core.Menu;
import com.yqboots.project.menu.core.MenuManager;
import com.yqboots.project.menu.core.MenuManagerImpl;
import com.yqboots.project.menu.core.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
@Configuration
@EnableConfigurationProperties(ProjectMenuProperties.class)
public class ProjectMenuAutoConfiguration {
    @Autowired
    private ProjectMenuProperties properties;

    @Bean
    public MenuManager projectMenuManager() {
        return new MenuManagerImpl(projectMenuRepository());
    }

    @Bean
    public MenuRepository projectMenuRepository() {
        return new MenuRepository(projectMenus());
    }

    private List<Menu> projectMenus() {
        return properties.getMenus();
    }
}
