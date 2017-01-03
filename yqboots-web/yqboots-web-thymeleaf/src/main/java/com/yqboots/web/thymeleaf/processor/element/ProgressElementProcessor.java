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

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * Progress Bar.
 *
 * @author Eric H B Zhan
 * @since 1.2.0
 */
public class ProgressElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_VALUE = "value";

    public ProgressElementProcessor() {
        super("progress");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final Configuration configuration = arguments.getConfiguration();
        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

        String value = element.getAttributeValue(ATTR_VALUE);
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, value);
        value = (String) expression.execute(configuration, arguments);

        nodes.add(build(StringUtils.defaultIfBlank(value, "0")));

        return nodes;
    }

    private Element build(final String valueAttrValue) {
        final Element container = new Element("div");
        container.setAttribute("class", "progress progress-u");

        container.addChild(buildProgressBar(valueAttrValue));

        return container;
    }

    private Element buildProgressBar(final String valueAttrValue) {
        final Element result = new Element("div");
        result.setAttribute("class", "progress-bar progress-bar-u");
        result.setAttribute("style", "width: " + valueAttrValue + "%");
        result.setAttribute("aria-valuemax", "100");
        result.setAttribute("aria-valuemin", "0");
        result.setAttribute("aria-valuenow", valueAttrValue);
        result.setAttribute("role", "progressbar");

        result.addChild(new Text(valueAttrValue + "%"));

        return result;
    }

    @Override
    public int getPrecedence() {
        return 2000;
    }
}
