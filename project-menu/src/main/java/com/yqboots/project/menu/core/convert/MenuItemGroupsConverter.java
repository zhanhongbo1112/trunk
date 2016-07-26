package com.yqboots.project.menu.core.convert;

import com.yqboots.project.menu.core.MenuItem;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-24.
 */
public class MenuItemGroupsConverter implements Converter<List<MenuItem>, Map<String, List<MenuItem>>> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Map<String, List<MenuItem>> convert(final List<MenuItem> source) {
        final Map<String, List<MenuItem>> results = new LinkedHashMap<>();

        for (final MenuItem menuItem : source) {
            String menuGroupKey = menuItem.getMenuGroup();
            if (results.containsKey(menuGroupKey)) {
                results.get(menuGroupKey).add(menuItem);
            } else {
                List<MenuItem> items = new ArrayList<>();
                items.add(menuItem);
                results.put(menuGroupKey, items);
            }
        }

        return results;
    }
}
