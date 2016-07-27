package com.yqboots.project.thymeleaf.dialect;

import com.yqboots.project.thymeleaf.processor.attr.PageSummaryAttrProcessor;
import com.yqboots.project.thymeleaf.processor.element.MenuElementProcessor;
import com.yqboots.project.thymeleaf.processor.element.OptionsElementProcessor;
import com.yqboots.project.thymeleaf.processor.element.PaginationElementProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016-07-09.
 */
public class YQBootsDialect extends AbstractDialect {
    /**
     * <p>
     * Returns the default dialect prefix (the one that will be used if none is explicitly
     * specified during dialect configuration).
     * </p>
     * <p>
     * If <tt>null</tt> is returned, then every attribute
     * and/or element is considered processable by the processors in the dialect that apply
     * to that kind of node (elements with their attributes), and not only those that start
     * with a specific prefix.
     * </p>
     * <p>
     * Prefixes are <b>not</b> exclusive to a dialect: several dialects can declare the same
     * prefix, effectively acting as an aggregate dialect.
     * </p>
     *
     * @return the dialect prefix.
     */
    @Override
    public String getPrefix() {
        return "yq";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new MenuElementProcessor());
        processors.add(new PageSummaryAttrProcessor());
        processors.add(new PaginationElementProcessor());
        processors.add(new OptionsElementProcessor());
        return processors;
    }
}
