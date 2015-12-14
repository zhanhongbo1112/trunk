package com.yqsoftwares.security.audit;

import com.yqsoftwares.security.core.User;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

/**
 * Created by Administrator on 2015-12-14.
 */
@Entity
@Table(name = "SEC_AUDIT")
@AssociationOverrides({@AssociationOverride(name = "createdBy", joinColumns = {@JoinColumn(name = "CREATED_BY", referencedColumnName = "USERNAME")}),
        @AssociationOverride(name = "lastModifiedBy", joinColumns = {@JoinColumn(name = "LAST_MODIFIED_BY", referencedColumnName = "USERNAME")})})
public class Audit extends AbstractAuditable<User, Long> {
    private int code;

    public Audit() {
        super();
    }

    public Audit(int code) {
        super();
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
