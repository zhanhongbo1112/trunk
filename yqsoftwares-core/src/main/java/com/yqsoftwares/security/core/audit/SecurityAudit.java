package com.yqsoftwares.security.core.audit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Administrator on 2015-12-15.
 */
@Entity
@DiscriminatorValue("SECURITY")
public class SecurityAudit extends Audit {
}
