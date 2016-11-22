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
package com.yqboots.web.thymeleaf.processor.element;

import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.convert.MenuGroupsConverter;
import com.yqboots.menu.core.convert.MenuItemGroupsConverter;
import com.yqboots.menu.core.MenuItemManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The element processor which processes the list of MenuItem in the menu bar.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class MenuElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_LAYOUT = "layout";

    private static final String LAYOUT_FULL_WIDTH = "full-width";

    private final MenuGroupsConverter menuGroupsConverter = new MenuGroupsConverter();
    private final MenuItemGroupsConverter menuItemGroupsConverter = new MenuItemGroupsConverter();

    public MenuElementProcessor() {
        super("menus");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 1000;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        // TODO: cache the nodes
        final SpringWebContext context = (SpringWebContext) arguments.getContext();
        final MenuItemManager manager = context.getApplicationContext().getBean(MenuItemManager.class);
        final List<MenuItem> menuItems = manager.getMenuItems();

        final String layoutAttrValue = element.getAttributeValue(ATTR_LAYOUT);
        if (StringUtils.equalsIgnoreCase(layoutAttrValue, LAYOUT_FULL_WIDTH)) {
            Map<String, Map<String, List<MenuItem>>> groups = menuGroupsConverter.convert(menuItems);
            nodes.addAll(new MenuGroupBuilder(arguments).build(groups));
        } else {
            // default layout
            Map<String, List<MenuItem>> groups = menuItemGroupsConverter.convert(menuItems);
            nodes.addAll(new MenuItemGroupBuilder(arguments).build(groups));
        }

        return nodes;
    }

    /**
     * Builds 3-level menus.
     *
     * @author Eric H B Zhan
     * @since 1.0.0
     */
    private class MenuGroupBuilder {
        private final Arguments arguments;

        public MenuGroupBuilder(final Arguments arguments) {
            this.arguments = arguments;
        }

        public Collection<? extends Node> build(final Map<String, Map<String, List<MenuItem>>> groups) {
            return groups.entrySet().stream().map(this::getMenuGroup).collect(Collectors.toList());
        }

        private Element getMenuGroup(final Map.Entry<String, Map<String, List<MenuItem>>> menuGroup) {
            final Element container = new Element("li");
            container.setAttribute("class", "dropdown mega-menu-fullwidth");
            final Element result = new Element("a");
            result.setAttribute("href", "javascript:void(0);");
            result.setAttribute("class", "dropdown-toggle");
            result.setAttribute("data-toggle", "dropdown");
            result.addChild(new Text(getMessage(arguments, "S0001." + menuGroup.getKey(), new Object[]{})));

            container.addChild(result);
            container.addChild(getMenuItemGroup(menuGroup.getValue()));

            return container;
        }

        private Element getMenuItemGroup(final Map<String, List<MenuItem>> menuItemGroup) {
            final Element result = new Element("ul");
            result.setAttribute("class", "dropdown-menu");

            final Element li = new Element("li");
            result.addChild(li);

            final Element div = new Element("div");
            div.setAttribute("class", "mega-menu-content disable-icons");
            li.addChild(div);

            final Element container = new Element("div");
            container.setAttribute("class", "container");
            div.addChild(container);

            final List<Map.Entry<String, List<MenuItem>>> entries = new ArrayList<>(menuItemGroup.entrySet());
            Element row = null;
            for (int i = 0; i < entries.size(); i++) {
                // a row contains 4 columns
                if (i % 4 == 0) {
                    row = new Element("div");
                    row.setAttribute("class", "row equal-height");
                    container.addChild(row);
                }

                Assert.notNull(row);
                row.addChild(getMenuItems(entries.get(i)));
            }

            return result;
        }

        private Element getMenuItems(final Map.Entry<String, List<MenuItem>> entry) {
            final Element result = new Element("div");
            result.setAttribute("class", "col-md-3 equal-height-in");

            final Element container = new Element("ul");
            container.setAttribute("class", "list-unstyled equal-height-list");

            final SpringWebContext context = (SpringWebContext) arguments.getContext();
            final String contextPath = context.getServletContext().getContextPath();

            container.addChild(getTitle(getMessage(arguments, "S0002." + entry.getKey(), new Object[]{})));
            for (final MenuItem item : entry.getValue()) {
                final Element menuItem = new Element("li");

                final Element link = new Element("a");
                link.setAttribute("href", StringUtils.join(contextPath, item.getUrl(), StringUtils.EMPTY));
                link.addChild(new Text(getMessage(arguments, "S0003." + item.getName(), new Object[]{})));
                menuItem.addChild(link);

                container.addChild(menuItem);
            }

            result.addChild(container);

            return result;
        }

        private Element getTitle(final String title) {
            final Element result = new Element("li");
            final Element h3 = new Element("h3");
            h3.addChild(new Text(title));
            result.addChild(h3);

            return result;
        }
    }

    /**
     * Builds 2-level menus.
     *
     * @author Eric H B Zhan
     * @since 1.0.0
     */
    private class MenuItemGroupBuilder {
        private final Arguments arguments;

        public MenuItemGroupBuilder(final Arguments arguments) {
            this.arguments = arguments;
        }

        public List<Node> build(final Map<String, List<MenuItem>> groups) {
            return groups.entrySet().stream().map(this::getMenuItemGroup).collect(Collectors.toList());
        }

        private Element getMenuItemGroup(final Map.Entry<String, List<MenuItem>> group) {
            final Element container = new Element("li");
            container.setAttribute("class", "dropdown");

            final Element result = new Element("a");
            result.setAttribute("href", "javascript:void(0);");
            result.setAttribute("class", "dropdown-toggle");
            result.setAttribute("data-toggle", "dropdown");
            result.addChild(new Text(getMessage(arguments, "S0001." + group.getKey(), new Object[]{})));

            container.addChild(result);
            container.addChild(getMenuItems(group));

            return container;
        }

        private Element getMenuItems(final Map.Entry<String, List<MenuItem>> group) {
            final Element result = new Element("ul");
            result.setAttribute("class", "dropdown-menu");

            final SpringWebContext context = (SpringWebContext) arguments.getContext();
            final String contextPath = context.getServletContext().getContextPath();
            for (final MenuItem item : group.getValue()) {
                final Element menuItem = new Element("li");

                final Element link = new Element("a");
                link.setAttribute("href", StringUtils.join(contextPath, item.getUrl(), StringUtils.EMPTY));
                link.addChild(new Text(getMessage(arguments, "S0003." + item.getName(), new Object[]{})));
                menuItem.addChild(link);

                result.addChild(menuItem);
            }

            return result;
        }
    }
}
