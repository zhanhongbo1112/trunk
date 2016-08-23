package com.yqboots.project.menu.core;

import com.yqboots.fss.core.support.FileType;
import com.yqboots.project.menu.autoconfigure.MenuItemProperties;
import com.yqboots.project.menu.core.repository.MenuItemRepository;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Administrator on 2016-06-28.
 */
@Transactional(readOnly = true)
public class MenuItemManagerImpl implements MenuItemManager {
    private final MenuItemRepository menuItemRepository;

    private final MenuItemProperties properties;

    private static Jaxb2Marshaller jaxb2Marshaller;

    static {
        jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(MenuItems.class, MenuItem.class);
    }

    public MenuItemManagerImpl(final MenuItemRepository menuItemRepository, final MenuItemProperties properties) {
        this.menuItemRepository = menuItemRepository;
        this.properties = properties;
    }

    @Override
    // TODO: @PostFilter for security
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItem(final String name) {
        return menuItemRepository.findByName(name);
    }

    @Override
    @Transactional
    public void imports(final InputStream inputStream) throws IOException {
        MenuItems menuItems = (MenuItems) jaxb2Marshaller.unmarshal(new StreamSource(inputStream));
        for (MenuItem item : menuItems.getMenuItems()) {
            MenuItem existOne = menuItemRepository.findByName(item.getName());
            if (existOne == null) {
                menuItemRepository.save(item);
                continue;
            }

            existOne.setUrl(item.getUrl());
            existOne.setMenuGroup(item.getMenuGroup());
            existOne.setMenuItemGroup(item.getMenuItemGroup());
            menuItemRepository.save(existOne);
        }
    }

    @Override
    public Path exports() throws IOException {
        final String fileName = properties.getExportFileNamePrefix() + LocalDate.now() + FileType.DOT_XML;

        if (!Files.exists(properties.getExportFileLocation())) {
            Files.createDirectories(properties.getExportFileLocation());
        }

        final Path result = Paths.get(properties.getExportFileLocation() + File.separator + fileName);

        final List<MenuItem> menuItems = menuItemRepository.findAll();

        try (FileWriter writer = new FileWriter(result.toFile())) {
            jaxb2Marshaller.marshal(new MenuItems(menuItems), new StreamResult(writer));
        }

        return result;
    }
}
