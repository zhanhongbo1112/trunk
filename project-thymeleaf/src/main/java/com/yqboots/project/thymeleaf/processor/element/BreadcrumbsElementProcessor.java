package com.yqboots.project.thymeleaf.processor.element;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItemManager;
import com.yqboots.project.thymeleaf.i18n.MessageKeys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-28.
 */
public class BreadcrumbsElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_MENU = "menu";

    private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbsElementProcessor.class);

    public BreadcrumbsElementProcessor() {
        super("breadcrumbs");
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

        final String menuAttrValue = element.getAttributeValue(ATTR_MENU);
        if (StringUtils.isBlank(menuAttrValue)) {
            throw new IllegalArgumentException("menu attribute should be set");
        }

        // get MenuItem by menu item's name
        final MenuItem menuItem = manager.getMenuItem(menuAttrValue);
        if (menuItem == null) {
            LOG.warn("Menu Item {} not found", menuAttrValue);
            return nodes;
        }

        nodes.add(buildBreadcrumbs(arguments, menuItem));

        return nodes;
    }

    private Element buildBreadcrumbs(final Arguments arguments, MenuItem menuItem) {
        final Element result = new Element("div");
        result.setAttribute("class", "breadcrumbs");

        final Element container = new Element("div");
        container.setAttribute("class", "container");
        container.addChild(buildLeftContent(arguments, menuItem));
        container.addChild(buildRightContent(arguments, menuItem));

        result.addChild(container);

        return result;
    }

    protected Element buildLeftContent(final Arguments arguments, MenuItem menuItem) {
        final Element h1 = new Element("h1");
        h1.setAttribute("class", "pull-left");
        h1.addChild(new Text(getMessage(arguments, MessageKeys.MENU + menuItem.getName(), new Object[]{})));

        return h1;
    }

    protected Element buildRightContent(final Arguments arguments, MenuItem menuItem) {
        final Element ul = new Element("ul");
        ul.setAttribute("class", "pull-right breadcrumb");

        final Element menuGroup = new Element("li");
        menuGroup.addChild(new Text(getMessage(arguments, MessageKeys.MENU_GROUP + menuItem.getMenuGroup(),
                new Object[]{})));
        ul.addChild(menuGroup);

        if (StringUtils.isNotBlank(menuItem.getMenuItemGroup())) {
            final Element menuItemGroup = new Element("li");
            menuItemGroup.addChild(new Text(getMessage(arguments, MessageKeys.MENU_ITEM_GROUP + menuItem.getMenuItemGroup(),
                    new Object[]{})));
            ul.addChild(menuItemGroup);
        }

        final Element leafMenuItem = new Element("li");
        leafMenuItem.setAttribute("class", "active");
        leafMenuItem.addChild(new Text(getMessage(arguments, MessageKeys.MENU + menuItem.getName(), new Object[]{})));
        ul.addChild(leafMenuItem);

        return ul;
    }
}
