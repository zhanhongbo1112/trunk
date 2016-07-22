package com.yqboots.project.thymeleaf.processor.element;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItemManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016-07-22.
 */
public class MenuElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public MenuElementProcessor() {
        super("menus");
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final SpringWebContext context = (SpringWebContext) arguments.getContext();

        final MenuItemManager manager = context.getApplicationContext().getBean(MenuItemManager.class);
        final List<MenuItem> menuItems = manager.getMenuItems();

        // TODO: cache the result
        final String contextPath = context.getServletContext().getContextPath();
        final Map<MenuContext, List<MenuItem>> groups = new LinkedHashMap<>();
        MenuContext menuContext;
        for (final MenuItem menuItem : menuItems) {
            String group = menuItem.getMenu();
            menuContext = new MenuContext(group, contextPath);
            if (groups.containsKey(menuContext)) {
                groups.get(menuContext).add(menuItem);
            } else {
                List<MenuItem> items = new ArrayList<>();
                items.add(menuItem);
                groups.put(menuContext, items);
            }
        }

        if (!groups.isEmpty()) {
            nodes.addAll(groups.entrySet().stream().map(this::getMenu).collect(Collectors.toList()));
        }

        return nodes;
    }

    private Element getMenu(final Map.Entry<MenuContext, List<MenuItem>> group) {
        final Element container = new Element("li");
        container.setAttribute("class", "dropdown");

        final Element result = new Element("a");
        result.setAttribute("href", "javascript:void(0);");
        result.setAttribute("class", "dropdown-toggle");
        result.setAttribute("data-toggle", "dropdown");
        result.addChild(new Text(group.getKey().getGroup()));

        container.addChild(result);
        container.addChild(getMenuItems(group));

        return container;
    }

    private Element getMenuItems(final Map.Entry<MenuContext, List<MenuItem>> group) {
        final Element result = new Element("ul");
        result.setAttribute("class", "dropdown-menu");

        final String contextPath = group.getKey().getContextPath();
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

    private static class MenuContext implements Serializable {
        private String group;

        private String contextPath;

        public MenuContext(final String group, final String contextPath) {
            this.group = group;
            this.contextPath = contextPath;
        }

        public String getGroup() {
            return group;
        }

        public String getContextPath() {
            return contextPath;
        }

        @Override
        public boolean equals(final Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
