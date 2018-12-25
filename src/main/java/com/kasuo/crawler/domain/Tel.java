package com.kasuo.crawler.domain;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
public class Tel {

    private Long id;
    private Long orgId;
    private String telNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
