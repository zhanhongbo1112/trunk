package com.yqsoftwares.cms.core;

import com.yqsoftwares.security.core.User;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2015-12-14.
 */
@Entity
@Table(name = "CMS_ITEM", uniqueConstraints = {@UniqueConstraint(name = "UN_ITEM_NAME", columnNames = {"NAME"})})
@AssociationOverrides({@AssociationOverride(name = "createdBy", joinColumns = {@JoinColumn(name = "CREATED_BY", referencedColumnName = "USERNAME")}),
        @AssociationOverride(name = "lastModifiedBy", joinColumns = {@JoinColumn(name = "LAST_MODIFIED_BY", referencedColumnName = "USERNAME")})})
public class Item extends AbstractAuditable<User, Long> {
    @NotEmpty
    @Length(max = 64)
    @Column(unique = true, length = 64, nullable = false)
    @NaturalId
    private String name;

    @NotNull
    @Length(max = 20)
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ItemState state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
