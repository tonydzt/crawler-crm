package com.kasuo.crawler.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/23
 */
public class Assignment {

    private Long id;
    private Long orgId;
    private Integer category;
    private String applicant;
    private String legalPerson;
    private String contact;
    private String address;
    private String trademark;
    private String registrationNo;
    private String date;
    private Boolean isAgain;
    private String assignmentName;
    private Integer telNum;
    private Integer mobileNum;
    private Boolean hasTel;

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAgain() {
        return isAgain;
    }

    public void setAgain(Boolean again) {
        isAgain = again;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public Integer getTelNum() {
        return telNum;
    }

    public void setTelNum(Integer telNum) {
        this.telNum = telNum;
    }

    public Integer getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(Integer mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Boolean getHasTel() {
        return hasTel;
    }

    public void setHasTel(Boolean hasTel) {
        this.hasTel = hasTel;
    }

    public List<Object> getExcelRow() {
        List<Object> row = new ArrayList<>();
        row.add(applicant);
        row.add(contact);
        row.add(address);
        row.add(null);
        row.add(null);
        row.add(null);
        row.add(trademark);
        row.add(registrationNo);
        row.add(date);
        row.add(category);
        row.add(assignmentName);
        row.add(legalPerson);
        row.add(null);
        row.add(null);
        row.add(isAgain ? "老客户新机会" : "新机会");
        return row;
    }

}
