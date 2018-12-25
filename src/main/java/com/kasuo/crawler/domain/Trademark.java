package com.kasuo.crawler.domain;

import java.util.Date;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
public class Trademark {

    private Long id;
    private Long excelStatusId;
    private Integer registrationNo;
    private String trademark;
    private String date;
    private String applicant;
    private String address;
    private String agent;
    private String service;
    private Integer status;
    private String mobile;
    private String tel;
    private Date insertTime;
    private Date updateTime;
    private Boolean isAgain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExcelStatusId() {
        return excelStatusId;
    }

    public void setExcelStatusId(Long excelStatusId) {
        this.excelStatusId = excelStatusId;
    }

    public Integer getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(Integer registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getAgain() {
        return isAgain;
    }

    public void setAgain(Boolean again) {
        isAgain = again;
    }
}
