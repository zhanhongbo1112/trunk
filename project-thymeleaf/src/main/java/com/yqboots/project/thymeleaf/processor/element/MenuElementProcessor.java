package com.yqboots.project.thymeleaf.processor.element;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItemManager;
import com.yqboots.project.menu.core.convert.MenuGroupsConverter;
import com.yqboots.project.menu.core.convert.MenuItemGroupsConverter;
import com.yqboots.project.thymeleaf.util.MenuGroupBuilder;
import com.yqboots.project.thymeleaf.util.MenuItemGroupBuilder;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.*;

/**
 * Created by Administrator on 2016-07-22.
 */
public class MenuElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_LAYOUT = "layout";

    private static final String LAYOUT_FULL_WIDTH = "full-width";

    private final MenuGroupsConverter menuGroupsConverter = new MenuGroupsConverter();
    private final MenuItemGroupsConverter menuItemGroupsConverter = new MenuItemGroupsConverter();

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

        // TODO: cache the result
        final SpringWebContext context = (SpringWebContext) arguments.getContext();
        final MenuItemManager manager = context.getApplicationContext().getBean(MenuItemManager.class);
        final List<MenuItem> menuItems = manager.getMenuItems();

        final String contextPath = context.getServletContext().getContextPath();
        final String layoutAttrValue = element.getAttributeValue(ATTR_LAYOUT);
        if (StringUtils.equalsIgnoreCase(layoutAttrValue, LAYOUT_FULL_WIDTH)) {
            Map<String, Map<String, List<MenuItem>>> groups = menuGroupsConverter.convert(menuItems);
            nodes.addAll(MenuGroupBuilder.build(groups, contextPath));
        } else {
            // default layout
            Map<String, List<MenuItem>> groups = menuItemGroupsConverter.convert(menuItems);
            nodes.addAll(MenuItemGroupBuilder.build(groups, contextPath));
        }

        return nodes;
    }
}
