package com.yqboots.project.thymeleaf.util;

import com.yqboots.project.menu.core.MenuItem;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-07-27.
 */
public final class MenuItemGroupBuilder {
    public static List<Node> build(final Map<String, List<MenuItem>> groups, final String contextPath) {
        return groups.entrySet().stream().map(entry -> getMenuItemGroup(entry, contextPath)).collect(Collectors.toList());
    }

    private static Element getMenuItemGroup(final Map.Entry<String, List<MenuItem>> group, final String contextPath) {
        final Element container = new Element("li");
        container.setAttribute("class", "dropdown");

        final Element result = new Element("a");
        result.setAttribute("href", "javascript:void(0);");
        result.setAttribute("class", "dropdown-toggle");
        result.setAttribute("data-toggle", "dropdown");
        result.addChild(new Text(group.getKey()));

        container.addChild(result);
        container.addChild(getMenuItems(group, contextPath));

        return container;
    }

    private static Element getMenuItems(final Map.Entry<String, List<MenuItem>> group, final String contextPath) {
        final Element result = new Element("ul");
        result.setAttribute("class", "dropdown-menu");

        for (final MenuItem item : group.getValue()) {
            final Element menuItem = new Element("li");

            final Element link = new Element("a");
            link.setAttribute("href", StringUtils.join(contextPath, item.getUrl(), StringUtils.EMPTY));
            link.addChild(new Text(item.getName()));
            menuItem.addChild(link);

            result.addChild(menuItem);
        }

        return result;
    }
}
