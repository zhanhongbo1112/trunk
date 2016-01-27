package com.yqsoftwares.security.core.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2015-12-14.
 */
@Entity
@Table(name = "SEC_AUDIT")
@Inheritance
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("DEFAULT")
public class Audit extends AbstractPersistable<Long> {
    @CreatedBy
    @Column(name = "CREATED_BY",length = 64, nullable = false)
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    private int code;

    public Audit() {
        super();
    }

    public Audit(int code) {
        super();
        this.code = code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return null == createdDate ? null : new Date(createdDate.getTime());
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = null == createdDate ? null : new Date(createdDate.getTime());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
