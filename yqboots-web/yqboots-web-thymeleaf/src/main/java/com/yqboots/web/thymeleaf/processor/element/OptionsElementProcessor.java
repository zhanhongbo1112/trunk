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
package com.yqboots.web.thymeleaf.processor.element;

import com.yqboots.web.thymeleaf.support.HtmlOption;
import com.yqboots.web.thymeleaf.support.HtmlElementResolvers;
import com.yqboots.web.thymeleaf.support.HtmlOptionsResolver;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * The element processor which processes the list of DataDict by the name.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class OptionsElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_NAME = "name";
    public static final String ATTR_VALUE_INCLUDED = "valueIncluded";
    public static final String ATTR_ATTRIBUTES = "attributes";

    public OptionsElementProcessor() {
        super("options");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final SpringWebContext context = (SpringWebContext) arguments.getContext();
        @SuppressWarnings({"uncheck"})
        final HtmlElementResolvers htmlElementResolvers = context.getApplicationContext().getBean(HtmlElementResolvers.class);

        final String nameAttrValue = element.getAttributeValue(ATTR_NAME);
        if (StringUtils.isBlank(nameAttrValue)) {
            throw new IllegalArgumentException("name attribute should be set");
        }

        final boolean valueIncluded = Boolean.valueOf(element.getAttributeValue(ATTR_VALUE_INCLUDED));

        final Configuration configuration = arguments.getConfiguration();
        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        String attributes = element.getAttributeValue(ATTR_ATTRIBUTES);
        if (StringUtils.isNotBlank(attributes)) {
            final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributes);
            attributes = (String) expression.execute(configuration, arguments);
        }

        Element option;

        List<HtmlOption> items = null;
        for (final HtmlOptionsResolver resolver : htmlElementResolvers.getHtmlOptionsResolvers()) {
            if (resolver.supports(nameAttrValue)) {
                items = resolver.getHtmlOptions(nameAttrValue, attributes);
                break;
            }
        }

        if (items != null) {
            for (HtmlOption item : items) {
                option = new Element("option");
                option.setAttribute("value", item.getValue());
                if (valueIncluded) {
                    option.addChild(new Text(StringUtils.join(new String[]{item.getValue(), item.getText()}, " - ")));
                } else {
                    option.addChild(new Text(item.getText()));
                }

                nodes.add(option);
            }
        }

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 1000 + 10;
    }
}
