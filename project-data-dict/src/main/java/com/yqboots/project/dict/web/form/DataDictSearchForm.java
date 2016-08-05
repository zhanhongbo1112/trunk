package com.yqboots.project.dict.web.form;

import com.yqboots.project.web.form.SearchForm;

/**
 * Created by Administrator on 2016-08-02.
 */
public class DataDictSearchForm implements SearchForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
