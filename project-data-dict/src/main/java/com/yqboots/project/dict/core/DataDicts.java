package com.yqboots.project.dict.core;

import com.yqboots.project.dict.core.DataDict;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016-08-12.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataDicts implements Serializable {
    @XmlElement(name = "dataDict", required = true)
    private List<DataDict> dataDicts;

    protected DataDicts() {
        super();
    }

    public DataDicts(final List<DataDict> dataDicts) {
        super();
        this.dataDicts = dataDicts;
    }

    public List<DataDict> getDataDicts() {
        return dataDicts;
    }

    public void setDataDicts(final List<DataDict> dataDicts) {
        this.dataDicts = dataDicts;
    }
}
