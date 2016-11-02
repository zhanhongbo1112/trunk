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
package com.yqboots.thymeleaf.processor.attr;

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
 * The attribute processor which processes the summary of paged data.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class PageSummaryAttrProcessor extends AbstractTextChildModifierAttrProcessor {
    public static final String ATTR_NAME = "pageSummary";

    public PageSummaryAttrProcessor() {
        super(ATTR_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        // process after StandardTextAttrProcessor
        return StandardTextAttrProcessor.ATTR_PRECEDENCE + 10;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getText(final Arguments arguments, final Element element, final String attributeName) {
        final Configuration configuration = arguments.getConfiguration();
        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        // Parse the attribute value as a Thymeleaf Standard Expression
        final String pageAttrValue = element.getAttributeValue(attributeName);
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, pageAttrValue);
        Page<?> page = (Page<?>) expression.execute(configuration, arguments);

        // for the first page, fixed "1 of 0 pages" error
        int pageNumber = page.getNumber() + 1;
        if (page.getTotalElements() == 0) {
            pageNumber = page.getNumber();
        }
        return getMessage(arguments, "S0004", new Object[]{page.getSize(), pageNumber, page.getTotalPages(),
                page.getTotalElements()});
    }
}
