package com.yqboots.security.core.audit.repository;

import com.yqboots.security.core.audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2015-12-14.
 */
public interface AuditRepository extends JpaRepository<Audit, Long>, JpaSpecificationExecutor<Audit> {
}
