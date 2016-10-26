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

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDictManager;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

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
        final DataDictManager manager = context.getApplicationContext().getBean(DataDictManager.class);

        final String nameAttrValue = element.getAttributeValue(ATTR_NAME);
        if (StringUtils.isBlank(nameAttrValue)) {
            throw new IllegalArgumentException("name attribute should be set");
        }

        final boolean valueIncluded = Boolean.valueOf(element.getAttributeValue(ATTR_VALUE_INCLUDED));
        final String attributes = element.getAttributeValue(ATTR_ATTRIBUTES);

        Element option;

        final List<DataDict> items = manager.getDataDicts(nameAttrValue, attributes);
        for (DataDict item : items) {
            option = new Element("option");
            option.setAttribute("value", item.getValue());
            if (valueIncluded) {
                option.addChild(new Text(StringUtils.join(new String[]{item.getValue(), item.getText()}, " - ")));
            } else {
                option.addChild(new Text(item.getText()));
            }

            nodes.add(option);
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
