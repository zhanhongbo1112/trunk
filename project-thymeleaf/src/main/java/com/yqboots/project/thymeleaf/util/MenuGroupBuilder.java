package com.yqboots.project.thymeleaf.util;

import com.yqboots.project.menu.core.MenuItem;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-07-27.
 */
public final class MenuGroupBuilder {
    public static Collection<? extends Node> build(final Map<String, Map<String, List<MenuItem>>> groups, final String contextPath) {
        return groups.entrySet().stream().map(entry -> getMenuGroup(entry, contextPath)).collect(Collectors.toList());
    }

    private static Element getMenuGroup(final Map.Entry<String, Map<String, List<MenuItem>>> menuGroup, final String contextPath) {
        final Element container = new Element("li");
        container.setAttribute("class", "dropdown mega-menu-fullwidth");
        final Element result = new Element("a");
        result.setAttribute("href", "javascript:void(0);");
        result.setAttribute("class", "dropdown-toggle");
        result.setAttribute("data-toggle", "dropdown");
        result.addChild(new Text(menuGroup.getKey()));

        container.addChild(result);
        container.addChild(getMenuItemGroup(menuGroup.getValue(), contextPath));

        return container;
    }

    private static Element getMenuItemGroup(final Map<String, List<MenuItem>> menuItemGroup, final String contextPath) {
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

            row.addChild(getMenuItems(entries.get(i), contextPath));
        }

        return result;
    }

    private static Element getMenuItems(final Map.Entry<String, List<MenuItem>> entry, final String contextPath) {
        final Element result = new Element("div");
        result.setAttribute("class", "col-md-3 equal-height-in");

        final Element container = new Element("ul");
        container.setAttribute("class", "list-unstyled equal-height-list");

        container.addChild(getTitle(entry.getKey()));
        for (final MenuItem item : entry.getValue()) {
            final Element menuItem = new Element("li");

            final Element link = new Element("a");
            link.setAttribute("href", StringUtils.join(contextPath, item.getUrl(), StringUtils.EMPTY));
            link.addChild(new Text(item.getName()));
            menuItem.addChild(link);

            container.addChild(menuItem);
        }

        result.addChild(container);

        return result;
    }

    private static Element getTitle(final String title) {
        final Element result = new Element("li");
        final Element h3 = new Element("h3");
        h3.addChild(new Text(title));
        result.addChild(h3);

        return result;
    }
}
