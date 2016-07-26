package com.yqboots.project.menu.core.convert;

import com.yqboots.project.menu.core.MenuItem;
import org.springframework.core.convert.converter.Converter;

import java.util.*;

/**
 * Created by Administrator on 2016-07-24.
 */
public class MenuGroupsConverter implements Converter<List<MenuItem>, Map<String, Map<String, List<MenuItem>>>> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Map<String, Map<String, List<MenuItem>>> convert(final List<MenuItem> source) {
        final Map<String, Map<String, List<MenuItem>>> results = new LinkedHashMap<>();

        Map<String, List<MenuItem>> menuGroup;
        for (final MenuItem menuItem : source) {
            String menuGroupKey = menuItem.getMenuGroup();
            if (results.containsKey(menuGroupKey)) {
                menuGroup = results.get(menuGroupKey);
                if (menuGroup.containsKey(menuItem.getMenuItemGroup())) {
                    menuGroup.get(menuItem.getMenuItemGroup()).add(menuItem);
                } else {
                    List<MenuItem> items = new ArrayList<>();
                    items.add(menuItem);
                    menuGroup.put(menuItem.getMenuItemGroup(), items);
                }
            } else {
                menuGroup = new LinkedHashMap<>();

                List<MenuItem> items = new ArrayList<>();
                items.add(menuItem);
                menuGroup.put(menuItem.getMenuItemGroup(), items);

                results.put(menuItem.getMenuGroup(), menuGroup);
            }
        }

        return results;
    }
}
