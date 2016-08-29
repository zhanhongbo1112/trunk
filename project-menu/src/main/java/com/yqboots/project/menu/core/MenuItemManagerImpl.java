/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.project.menu.core;

import com.yqboots.project.fss.core.support.FileType;
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
 * It Manages the MenuItem related functions.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Transactional(readOnly = true)
public class MenuItemManagerImpl implements MenuItemManager {
    private final MenuItemRepository menuItemRepository;

    private final MenuItemProperties properties;

    /**
     * For marshalling and unmarshalling Data Dictionaries.
     */
    private static Jaxb2Marshaller jaxb2Marshaller;

    static {
        jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(MenuItems.class, MenuItem.class);
    }

    public MenuItemManagerImpl(final MenuItemRepository menuItemRepository, final MenuItemProperties properties) {
        this.menuItemRepository = menuItemRepository;
        this.properties = properties;
    }

    /**
     * Gets all menu items.
     *
     * @return list of MenuItem
     */
    @Override
    // TODO: @PostFilter for security
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    /**
     * Gets MenuItem by its name
     *
     * @param name the name of the MenuItem
     * @return the MenuItem
     */
    @Override
    public MenuItem getMenuItem(final String name) {
        return menuItemRepository.findByName(name);
    }

    /**
     * Imports an XML-presented file, which contains menu items.
     *
     * @param inputStream the file stream
     * @throws IOException if failed
     */
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

    /**
     * Exports all menu items to a file for downloading.
     *
     * @return the exported file path
     * @throws IOException if failed
     */
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
