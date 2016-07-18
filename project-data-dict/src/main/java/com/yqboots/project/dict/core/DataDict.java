package com.yqboots.project.dict.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016-07-19.
 */
@Entity
@Table(name = "PRJ_DICT", indexes = {@Index(name = "IDX_DICT_NAME", columnList = "name")})
public class DataDict extends AbstractPersistable<Long> {
    @Column(length = 32)
    private String name;

    @Column(length = 128)
    private String text;

    @Column(length = 64)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

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
}
