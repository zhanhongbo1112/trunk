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
@Inheritance
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
@AssociationOverrides({@AssociationOverride(name = "createdBy", joinColumns = {@JoinColumn(referencedColumnName = "USERNAME", nullable = false)}),
        @AssociationOverride(name = "lastModifiedBy", joinColumns = {@JoinColumn(referencedColumnName = "USERNAME", nullable = false)})})
@AttributeOverrides({@AttributeOverride(name = "createdDate", column = @Column(nullable = false)),
        @AttributeOverride(name = "lastModifiedDate", column = @Column(nullable = false))})
public class Item extends AbstractAuditable<User, Long> {
    @NotEmpty
    @Length(max = 64)
    @Column(unique = true, length = 64, nullable = false)
    @NaturalId
    private String name;

    @NotNull
    @Length(max = 32)
    @Enumerated(EnumType.STRING)
    @Column(length = 32, nullable = false)
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
