package com.kasuo.crawler.domain;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
public class Email {

    private Long id;
    private Long orgId;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


