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
package com.yqboots.project.thymeleaf.processor.element;

import com.yqboots.project.thymeleaf.processor.support.AlertLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.VariablesMap;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The alert message, which contains a hidden button, title and messages.
 * <p>
 * <p>
 * <div class="alert alert-warning fade in" th:if="${not #arrays.isEmpty(messages)}">
 * <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
 * <h4><i class="glyphicon glyphicon-warning-sign"></i>You got an error!</h4>
 * <ul class="list-unstyled">
 * <li th:each="message : ${messages}" th:text="${message}">Input is incorrect</li>
 * </ul>
 * </div>
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class AlertElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String DEFAULT_LEVEL = "warning";

    public static final String ATTR_LEVEL = "level";

    public AlertElementProcessor() {
        super("alert");
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final String levelAttrValue = StringUtils.defaultIfBlank(element.getAttributeValue(ATTR_LEVEL), DEFAULT_LEVEL);

        final VariablesMap<String, Object> variables = arguments.getContext().getVariables();
        variables.values().stream().filter(new Predicate<Object>() {
            @Override
            public boolean test(final Object o) {
                return BindingResult.class.isAssignableFrom(o.getClass());
            }
        }).forEach(new Consumer<Object>() {
            @Override
            public void accept(final Object value) {
                BindingResult bindingResult = (BindingResult) value;
                if (bindingResult.hasGlobalErrors()) {
                    nodes.add(build(arguments, bindingResult.getGlobalErrors(), levelAttrValue));
                }
            }
        });

        return nodes;
    }

    /**
     * Builds the alert node.
     *
     * @param arguments the arguments
     * @param errors    the errors displayed
     * @param level     the alert level
     * @return the alert node
     */
    private Node build(final Arguments arguments, final List<ObjectError> errors, final String level) {
        final Element container = new Element("div");
        container.setAttribute("class", AlertLevel.getStyleClass(level) + " fade in");

        container.addChild(buildHiddenButton());
        container.addChild(buildTitle(arguments));
        container.addChild(buildMessages(arguments, errors));

        return container;
    }

    /**
     * Builds message nodes.
     *
     * @param arguments the arguments
     * @param errors    the errors displayed
     * @return the message nodes
     */
    private Node buildMessages(final Arguments arguments, final List<ObjectError> errors) {
        final Element result = new Element("ul");
        result.setAttribute("class", "list-unstyled");

        Element li;
        for (final ObjectError error : errors) {
            li = new Element("li");
            li.addChild(new Text(getMessage(arguments, error.getCode(), error.getArguments())));
            result.addChild(li);
        }

        return result;
    }

    /**
     * Builds the title node.
     *
     * @param arguments the arguments
     * @return the title node
     */
    private Node buildTitle(final Arguments arguments) {
        final Element result = new Element("h4");

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-warning-sign");

        result.addChild(icon);

        result.addChild(new Text(getMessage(arguments, "S0005", new Object[]{})));

        return result;
    }

    /**
     * Builds the hidden button node.
     *
     * @return the hidden button node
     */
    private static Node buildHiddenButton() {
        final Element result = new Element("button");
        result.setAttribute("type", "button");
        result.setAttribute("class", "close");
        result.setAttribute("data-dismiss", "alert");
        result.setAttribute("aria-hidden", "true");

        result.addChild(new Text("×"));

        return result;
    }
}
