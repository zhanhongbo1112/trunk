package com.yqboots.web.thymeleaf.support;

import java.util.ArrayList;
import java.util.List;

/**
 * The container for all registered {@link HtmlOptionsResolver}.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class HtmlOptionsSupport {
    private List<HtmlOptionsResolver> resolvers = new ArrayList<>();

    public HtmlOptionsSupport(final List<HtmlOptionsResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public List<HtmlOptionsResolver> getResolvers() {
        return resolvers;
    }
}
