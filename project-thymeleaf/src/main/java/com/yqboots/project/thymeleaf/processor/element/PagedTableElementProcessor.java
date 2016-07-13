package com.yqboots.project.thymeleaf.processor.element;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.processor.attr.SpringHrefAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-14.
 */
public class PagedTableElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_TITLE = "title";
    public static final String ATTR_PAGE = "page";

    protected PagedTableElementProcessor() {
        super("pagedTable");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final Configuration configuration = arguments.getConfiguration();

        // Obtain the attribute value
        final IWebContext context = (IWebContext) arguments.getContext();
        final String contextPath = StringUtils.defaultIfEmpty(element.getAttributeValue(SpringHrefAttrProcessor.ATTR_NAME),
                context.getServletContext().getContextPath());

        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        // Parse the attribute value as a Thymeleaf Standard Expression
        final String pageAttrValue = element.getAttributeValue(ATTR_PAGE);
        IStandardExpression expression = parser.parseExpression(configuration, arguments, pageAttrValue);
        final Page<?> page = (Page<?>) expression.execute(configuration, arguments);

        // Parse the attribute value as a Thymeleaf Standard Expression
        final String titleAttrValue = element.getAttributeValue(ATTR_TITLE);
        expression = parser.parseExpression(configuration, arguments, titleAttrValue);
        final String title = (String) expression.execute(configuration, arguments);

        final Element container = new Element("div");
        container.setAttribute("class", "panel");
        addHeading(title, container);
        addTable(page, container);
        addPagination(page, container);

        nodes.add(container);

        return nodes;
    }

    private String getAnnotatedClass(Page<?> page) {
        String result = null;
        if (!page.getContent().isEmpty()) {
            result = page.getContent().get(0).getClass().getName();
        }

        return result;
    }

    private void addHeading(final String title, Element container) {
        Element result = new Element("div");
        result.setAttribute("class", "panel-heading");

        Element h3 = new Element("h3");
        h3.addChild(new Text(title));
        result.addChild(h3);

        container.addChild(result);
    }

    private void addTable(Page<?> page, Element container) {

    }

    private void addPagination(Page<?> page, Element container) {

    }

    @Override
    public int getPrecedence() {
        return SpringHrefAttrProcessor.ATTR_PRECEDENCE + 10;
    }
}
