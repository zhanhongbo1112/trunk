package com.yqboots.project.thymeleaf.processor.attr;

import org.springframework.data.domain.Page;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.standard.processor.attr.StandardTextAttrProcessor;

/**
 * Created by Administrator on 2016-07-09.
 */
public class PageSummaryAttrProcessor extends AbstractTextChildModifierAttrProcessor {
    public static final String ATTR_NAME = "page";

    public PageSummaryAttrProcessor() {
        super("pageSummary");
    }

    @Override
    public int getPrecedence() {
        // process after StandardTextAttrProcessor
        return StandardTextAttrProcessor.ATTR_PRECEDENCE + 10;
    }

    @Override
    protected String getText(final Arguments arguments, final Element element, final String attributeName) {
        final Configuration configuration = arguments.getConfiguration();
        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        // Parse the attribute value as a Thymeleaf Standard Expression
        final String pageAttrValue = element.getAttributeValue(ATTR_NAME);
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, pageAttrValue);
        final Page<?> page = (Page<?>) expression.execute(configuration, arguments);

        return getMessage(arguments, "page.summary", new Object[]{page.getSize(), page.getNumber() + 1,
                page.getTotalPages(), page.getTotalElements()});
    }
}
