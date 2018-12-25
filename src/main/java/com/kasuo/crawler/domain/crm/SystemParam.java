package com.kasuo.crawler.domain.crm;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.SystemParamCls;

public class SystemParam
        extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected String name = null;
    protected String value = null;
    protected SystemParamCls cls = new SystemParamCls("1");
    protected String memo = null;

    public SystemParam() {
    }

    public SystemParam(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SystemParamCls getCls() {
        return this.cls;
    }

    public void setCls(SystemParamCls cls) {
        this.cls = cls;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
