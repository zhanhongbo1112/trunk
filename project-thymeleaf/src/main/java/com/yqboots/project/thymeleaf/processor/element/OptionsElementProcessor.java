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
 * Created by Administrator on 2016-07-28.
 */
public class OptionsElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String ATTR_NAME = "name";

    public OptionsElementProcessor() {
        super("options");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final SpringWebContext context = (SpringWebContext) arguments.getContext();
        final DataDictManager manager = context.getApplicationContext().getBean(DataDictManager.class);

        final String nameAttrValue = element.getAttributeValue(ATTR_NAME);
        if (StringUtils.isBlank(nameAttrValue)) {
            throw new IllegalArgumentException("name attribute should be set");
        }

        Element option;

        final List<DataDict> items = manager.getDataDicts(nameAttrValue);
        for (DataDict item : items) {
            option = new Element("option");
            option.setAttribute("value", item.getValue());
            option.addChild(new Text(item.getText()));
            nodes.add(option);
        }

        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 1000 + 10;
    }
}
