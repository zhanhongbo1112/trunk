package com.yqboots.project.thymeleaf.processor.element;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-09.
 */
public class PaginationElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public PaginationElementProcessor() {
        super("pagination");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        final Element container = new Element("div");
        container.setAttribute("class", "pagination");
        final Text text = new Text("Hello Pagination");
        container.addChild(text);

        nodes.add(container);

        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
