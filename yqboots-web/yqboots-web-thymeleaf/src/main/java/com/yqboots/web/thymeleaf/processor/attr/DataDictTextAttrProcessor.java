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
package com.yqboots.web.thymeleaf.processor.attr;

import com.yqboots.dict.facade.DataDictFacade;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.standard.processor.attr.StandardTextAttrProcessor;

/**
 * The attribute processor which converts the data dict value to text for a specified dict name..
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class DataDictTextAttrProcessor extends AbstractTextChildModifierAttrProcessor {
    public static final String ATTR_NAME = "dict";

    public DataDictTextAttrProcessor() {
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
        // Parse the attribute value as a Thymeleaf Standard Expression
        final String dictName = element.getAttributeValue(attributeName);
        if (StringUtils.isBlank(dictName)) {
            return StringUtils.EMPTY;
        }

        Node node = element.getFirstChild();
        if (!(node instanceof Text)) {
            return StringUtils.EMPTY;
        }

        final String dictValue = ((Text) node).getContent();
        if (StringUtils.isNotBlank(dictValue)) {
            final SpringWebContext context = (SpringWebContext) arguments.getContext();
            final DataDictFacade dataDictFacade = context.getApplicationContext().getBean(DataDictFacade.class);
            return dataDictFacade.getText(dictName, dictValue);
        }

        return dictValue;
    }
}
