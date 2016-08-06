package com.yqboots.project.menu.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

/**
 * Created by Administrator on 2016-06-28.
 */
@Entity
@Table(name = "PRJ_MENUITEM", indexes = {
        @Index(name = "IDX_MENU_NAME", columnList = "name", unique = true)
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuItem extends AbstractPersistable<Long> {
    @Column(nullable = false, length = 32, unique = true)
    @XmlElement(required = true)
    private String name;

    @Column(nullable = false, length = 64)
    @XmlElement(required = true)
    private String url;

    @Column(nullable = false, length = 32)
    @XmlElement(required = true)
    private String menuGroup;

    @Column(length = 32)
    private String menuItemGroup;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final String menuGroup) {
        this.menuGroup = menuGroup;
    }

    public String getMenuItemGroup() {
        return menuItemGroup;
    }

    public void setMenuItemGroup(final String menuItemGroup) {
        this.menuItemGroup = menuItemGroup;
    }
}
