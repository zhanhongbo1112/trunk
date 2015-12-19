package com.yqsoftwares.cms.core;

import com.yqsoftwares.security.core.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2015-12-19.
 */
@Entity
@DiscriminatorValue(value = "1")
public class AssignableItem extends Item {
    @Valid
    @ManyToOne
    @JoinColumn(name = "ASSIGNER", referencedColumnName = "USERNAME")
    private User assigner;

    @NotEmpty
    @Length(max = 255)
    @Column(name = "ASSIGNEE", length = 255)
    private String assignee;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ASSIGNED_DATE")
    private Date assignedDate;

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
}
