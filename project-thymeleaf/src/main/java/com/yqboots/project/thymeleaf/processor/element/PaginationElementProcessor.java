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
    private static final int DEFAULT_STEPS = 5;

    public PaginationElementProcessor() {
        super("pagination");
    }

    private static String getPagedParams(String contextPath, int page, int size) {
        StringBuilder sb = new StringBuilder(contextPath);
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
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final Configuration configuration = arguments.getConfiguration();

        // Obtain the attribute value
        IWebContext context = (IWebContext) arguments.getContext();
        String contextPath = StringUtils.defaultIfEmpty(element.getAttributeValue("href"),
                context.getServletContext().getContextPath());

        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        // Parse the attribute value as a Thymeleaf Standard Expression
        String pageAttrValue = element.getAttributeValue("page");
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, pageAttrValue);
        Page<?> page = (Page<?>) expression.execute(configuration, arguments);

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

    private void addStepElements(Page<?> page, String contextPath, Element container) {
        for (int step = 0; step < DEFAULT_STEPS; step++) {
            // less than DEFAULT STEPS
            if (page.getTotalPages() < step + 1) {
                continue;
            }
            final Element li = new Element("li");

            int stepValue;

            final Element a = new Element("a");
            if ((page.getNumber() + DEFAULT_STEPS) <= page.getTotalPages()) {
                a.setAttribute("href", getPagedParams(contextPath, page.getNumber() + step, page.getSize()));

                stepValue = page.getNumber() + step + 1;
                a.addChild(new Text(stepValue + ""));
            } else if (page.getTotalPages() < DEFAULT_STEPS && page.getTotalPages() >= step + 1) {
                a.setAttribute("href", getPagedParams(contextPath, step, page.getSize()));

                stepValue = step + 1;
                a.addChild(new Text(stepValue + ""));
            } else {
                a.setAttribute("href", getPagedParams(contextPath, page.getTotalPages() - DEFAULT_STEPS + step, page.getSize()));

                stepValue = page.getTotalPages() - DEFAULT_STEPS + step + 1;
                a.addChild(new Text(stepValue + ""));
            }
            li.addChild(a);

            // set active
            if (page.getNumber() + 1 == stepValue) {
                li.setAttribute("class", "active");
            }

            container.addChild(li);
        }
    }

    private Element getFirstElement(Page<?> page, final String contextPath) {
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

    private Element getPreviousElement(Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        final Element a = new Element("a");
        if (page.isFirst()) {
            a.setAttribute("href", getPagedParams(contextPath, 0, page.getSize()));
        } else {
            a.setAttribute("href", getPagedParams(contextPath, page.getNumber() - 1, page.getSize()));
        }

        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-triangle-left");
        a.addChild(icon);

        if (page.isFirst()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }

    private Element getNextElement(Page<?> page, final String contextPath) {
        final Element result = new Element("li");

        final Element a = new Element("a");
        if (page.isLast()) {
            a.setAttribute("href", getPagedParams(contextPath, page.getTotalPages() - 1, page.getSize()));
        } else {
            a.setAttribute("href", getPagedParams(contextPath, page.getNumber() + 1, page.getSize()));
        }
        result.addChild(a);

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-triangle-right");
        a.addChild(icon);

        if (page.isLast()) {
            result.setAttribute("class", "disabled");
        }

        return result;
    }

    private Element getLastElement(Page<?> page, final String contextPath) {
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

    @Override
    public int getPrecedence() {
        // process after SpringHrefAttrProcessor
        return SpringHrefAttrProcessor.ATTR_PRECEDENCE + 10;
    }
}
