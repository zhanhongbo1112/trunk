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

import com.yqboots.web.thymeleaf.support.HtmlElementResolvers;
import com.yqboots.web.thymeleaf.support.HtmlTree;
import com.yqboots.web.thymeleaf.support.HtmlTreeNode;
import com.yqboots.web.thymeleaf.support.HtmlTreeResolver;
import org.apache.commons.collections.CollectionUtils;
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
 * The element processor which generates tree view.
 *
 * @author Eric H B Zhan
 * @since 1.1.1
 */
public class TreeElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_NAME = "name";
    public static final String ATTR_ATTRIBUTES = "attributes";

    public TreeElementProcessor() {
        super("tree");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        // Obtain the attribute value
        final SpringWebContext context = (SpringWebContext) arguments.getContext();
        @SuppressWarnings({"uncheck"})
        final HtmlElementResolvers htmlElementResolvers = context.getApplicationContext().getBean(HtmlElementResolvers.class);

        // Parse the attribute value as a Thymeleaf Standard Expression
        final String nameAttrValue = element.getAttributeValue(ATTR_NAME);
        if (StringUtils.isBlank(nameAttrValue)) {
            throw new IllegalArgumentException("name attribute should be set");
        }

        final Configuration configuration = arguments.getConfiguration();
        // Obtain the Thymeleaf Standard Expression parser
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        String attributes = element.getAttributeValue(ATTR_ATTRIBUTES);
        if (StringUtils.isNotBlank(attributes)) {
            final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributes);
            attributes = (String) expression.execute(configuration, arguments);
        }

        HtmlTree tree = null;
        for (HtmlTreeResolver htmlTreeResolver : htmlElementResolvers.getHtmlTreeResolvers()) {
            if (htmlTreeResolver.supports(nameAttrValue)) {
                tree = htmlTreeResolver.getHtmlTree(nameAttrValue, attributes);
                break;
            }
        }

        if (tree != null && CollectionUtils.isNotEmpty(tree.getNodes())) {
            final Element root = new Element("ul");

            final Element li = new Element("li");
            li.setAttribute("id", tree.getId());
            li.addChild(new Text(tree.getText()));
            li.addChild(buildTree(tree));

            root.addChild(li);

            nodes.add(root);
        }

        return nodes;
    }

    private Element buildTree(final HtmlTree tree) {
        final Element container = new Element("ul");

        for (HtmlTreeNode node : tree.getNodes()) {
            container.addChild(buildTreeNode(node));
        }

        return container;
    }

    private Element buildTreeNode(final HtmlTreeNode node) {
        final Element li = new Element("li");
        li.setAttribute("id", node.getId());
        li.addChild(new Text(node.getText()));

        if (!node.isLeaf() || CollectionUtils.isNotEmpty(node.getChildren())) {
            final Element subNode = new Element("ul");

            for (HtmlTreeNode child : node.getChildren()) {
                subNode.addChild(buildTreeNode(child));
            }

            li.addChild(subNode);
        }

        return li;
    }

    @Override
    public int getPrecedence() {
        return 2000;
    }
}
