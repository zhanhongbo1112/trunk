package com.yqsoftwares.security.audit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015-12-15.
 */
@Entity
@DiscriminatorValue("1")
public class SecurityAudit extends Audit {
}
