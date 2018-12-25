package com.kasuo.crawler.domain.core;

import java.util.Date;

/**
 * @author douzhitong
 * @date 18/11/17
 */
public class GenericEntity {

    protected String id = null;
    protected String name = null;
    protected String description = null;
    protected Date createTime = new Date();
    protected Date lastModifyTime = null;

    public GenericEntity() {}

    public GenericEntity(String id)
    {
        this.id = id;
    }

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

    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getLastModifyTime()
    {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
