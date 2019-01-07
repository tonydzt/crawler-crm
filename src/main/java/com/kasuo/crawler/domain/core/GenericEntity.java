package com.kasuo.crawler.domain.core;

import java.util.Date;

/**
 * @author douzhitong
 * @date 18/11/17
 */
public class GenericEntity {

    protected String id = null;
    protected String name = null;
    protected Date createTime = new Date();

    public GenericEntity() {}

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
