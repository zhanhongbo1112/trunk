package com.yqboots.project.menu.core.repository;

import com.yqboots.project.menu.core.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2016-06-28.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {
    MenuItem findByName(String name);

    Page<MenuItem> findByNameLikeIgnoreCaseOrderByName(String name, Pageable pageable);
}
