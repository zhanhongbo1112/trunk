package com.yqboots.web.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-02-03.
 */
public class OneToManyHolder<O extends Serializable, M extends Serializable> implements Serializable {
    O one;
    M[] many;

    public OneToManyHolder() {
        super();
    }

    public OneToManyHolder(O one, M[] many) {
        super();
        this.one = one;
        this.many = many;
    }

    public O getOne() {
        return one;
    }

    public void setOne(O one) {
        this.one = one;
    }

    public M[] getMany() {
        return many;
    }

    public void setMany(M[] many) {
        this.many = many;
    }
}
