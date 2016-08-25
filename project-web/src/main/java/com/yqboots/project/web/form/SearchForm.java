package com.yqboots.project.web.form;

import java.io.Serializable;

/**
 * Stub interface for the html form of search function.
 * Created by Administrator on 2016-08-04.
 */
public class SearchForm<T> implements Serializable {
    private T criterion;

    public SearchForm() {
    }

    public SearchForm(final T criterion) {
        this.criterion = criterion;
    }

    public T getCriterion() {
        return criterion;
    }

    public void setCriterion(final T criterion) {
        this.criterion = criterion;
    }
}
