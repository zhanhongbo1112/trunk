package com.yqboots.project.dict.core.repository;

import com.yqboots.project.dict.core.DataDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2016-07-19.
 */
public interface DataDictRepository extends JpaRepository<DataDict, Long>, JpaSpecificationExecutor<DataDict> {
    List<DataDict> findByNameOrderByText(String name);

    DataDict findByNameAndValue(String name, String value);

    Page<DataDict> findByNameLikeIgnoreCaseOrderByName(String name, Pageable pageable);
}
