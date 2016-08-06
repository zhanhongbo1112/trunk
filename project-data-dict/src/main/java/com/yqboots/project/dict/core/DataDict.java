package com.yqboots.project.dict.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

/**
 * Created by Administrator on 2016-07-19.
 */
@Entity
@Table(name = "PRJ_DATA_DICT", indexes = {
        @Index(name = "IDX_DICT_NAME", columnList = "name"),
        @Index(name = "IDX_DICT_NAME_VALUE", columnList = "name, value", unique = true)
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataDict extends AbstractPersistable<Long> {
    @Column(length = 32, nullable = false)
    @XmlElement(required = true)
    private String name;

    @Column(length = 64, nullable = false)
    @XmlElement(required = true)
    private String text;

    @Column(length = 32, nullable = false)
    @XmlElement(required = true)
    private String value;

    @Column(length = 255)
    private String description;

    /**
     * Sets the id of the entity.
     *
     * @param id the id to set
     */
    @Override
    public void setId(final Long id) {
        super.setId(id);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
