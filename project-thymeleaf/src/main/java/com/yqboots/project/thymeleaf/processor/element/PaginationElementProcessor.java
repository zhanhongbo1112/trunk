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
 * Created by Administrator on 2016-07-09.
 */
public class PaginationElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_NAME = "page";
    private static final int DEFAULT_STEPS = 5;

    public PaginationElementProcessor() {
        super("pagination");
    }

    /**
     * Gets the pagination url based on the context path.
     *
     * @param contextPath current context path of servlet
     * @param page        the result page number
     * @param size        the size per page
     * @return the url
     */
    private static String getPagedParams(final String contextPath, final int page, final int size) {
        final StringBuilder sb = new StringBuilder(contextPath);
        // can contain params, as it should be
        if (contextPath.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }

        sb.append("page=").append(page).append("&size=").append(size);

        return sb.toString();
    }

    @Override
    public int getPrecedence() {
        // process after SpringHrefAttrProcessor
        return SpringHrefAttrProcessor.ATTR_PRECEDENCE + 10;
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
        final String pageAttrValue = element.getAttributeValue(ATTR_NAME);
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, pageAttrValue);
        final Page<?> page = (Page<?>) expression.execute(configuration, arguments);

        final Element container = new Element("ul");
        container.setAttribute("class", "pagination");

        container.addChild(getFirstElement(page, contextPath));
        container.addChild(getPreviousElement(page, contextPath));
        addStepElements(page, contextPath, container);
        container.addChild(getNextElement(page, contextPath));
        container.addChild(getLastElement(page, contextPath));

        nodes.add(container);

        return nodes;
    }

    /**
     * Adds elements for steps.
     *
     * @param page        the pagination object
     * @param contextPath current context path of servlet
     * @param container   the container node of the element
     */
    private void addStepElements(final Page<?> page, final String contextPath, final Element container) {
        for (int step = 0; step < DEFAULT_STEPS; step++) {
            // beyond total pages is not allowed
            if (page.getTotalPages() < step + 1) {
                continue;
            }

            String url;
            int stepValue;
            if ((page.getNumber() + DEFAULT_STEPS) <= page.getTotalPages()) {
                // calculate by page number
                url = getPagedParams(contextPath, page.getNumber() + step, page.getSize());
                stepValue = page.getNumber() + step + 1;
            } else if (page.getTotalPages() < DEFAULT_STEPS && page.getTotalPages() >= step + 1) {
                // between step and DEFAULT_STEPS
                url = getPagedParams(contextPath, step, page.getSize());
                stepValue = step + 1;
            } else {
                // calculate by totalPages
                url = getPagedParams(contextPath, page.getTotalPages() - DEFAULT_STEPS + step, page.getSize());
                stepValue = page.getTotalPages() - DEFAULT_STEPS + step + 1;
            }

            final Element a = new Element("a");
            a.setAttribute("href", url);
            a.addChild(new Text(Integer.toString(stepValue)));

            final Element li = new Element("li");
            li.addChild(a);
            // set active
            if (page.getNumber() + 1 == stepValue) {
                li.setAttribute("class", "active");
            }

            container.addChild(li);
        }
    }

    /**
     * Gets the link node of the first page.
     *
     * @param page        the pagination object
     * @param contextPath current context path of servlet
     * @return the node
     */
    private Element getFirstElement(final Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        final Element a = new Element("a");
        a.setAttribute("href", getPagedParams(contextPath, 0, page.getSize()));
        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-step-backward");
        a.addChild(icon);

        if (page.isFirst()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }

    /**
     * Gets the link node of the previous page.
     *
     * @param page        the pagination object
     * @param contextPath current context path of servlet
     * @return the node
     */
    private Element getPreviousElement(final Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        String url;
        if (!page.hasPrevious()) {
            url = getPagedParams(contextPath, 0, page.getSize());
        } else {
            url = getPagedParams(contextPath, page.getNumber() - 1, page.getSize());
        }

        final Element a = new Element("a");
        a.setAttribute("href", url);
        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-triangle-left");

        a.addChild(icon);

        if (!page.hasPrevious()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }

    /**
     * Gets the link node of the next page.
     *
     * @param page        the pagination object
     * @param contextPath current context path of servlet
     * @return the node
     */
    private Element getNextElement(final Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        String url;
        if (!page.hasNext()) {
            url = getPagedParams(contextPath, page.getTotalPages() - 1, page.getSize());
        } else {
            url = getPagedParams(contextPath, page.getNumber() + 1, page.getSize());
        }

        final Element a = new Element("a");
        a.setAttribute("href", url);
        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-triangle-right");

        a.addChild(icon);

        if (!page.hasNext()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }

    /**
     * Gets the link node of the last page.
     *
     * @param page        the pagination object
     * @param contextPath current context path of servlet
     * @return the node
     */
    private Element getLastElement(final Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        final Element a = new Element("a");
        a.setAttribute("href", getPagedParams(contextPath, page.getTotalPages() - 1, page.getSize()));
        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-step-forward");

        a.addChild(icon);

        if (page.isLast()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }
}
