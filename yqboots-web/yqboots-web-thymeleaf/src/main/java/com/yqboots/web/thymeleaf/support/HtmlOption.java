package com.yqboots.web.thymeleaf.support;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Html select option.
 *
 * @author Eric H B Zhan
 * @since 1.1.0
 */
public class HtmlOption implements Serializable {
    private String text;

    private String value;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("value", value)
                .toString();
    }
}
