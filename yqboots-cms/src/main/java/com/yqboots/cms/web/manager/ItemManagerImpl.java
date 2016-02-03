package com.yqboots.cms.web.manager;

import com.yqboots.cms.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015-12-19.
 */
@Service
@Transactional(readOnly = true)
public class ItemManagerImpl implements ItemManager {
    @Autowired
    private ItemRepository itemRepository;
}
