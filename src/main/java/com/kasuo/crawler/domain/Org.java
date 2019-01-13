package com.kasuo.crawler.domain;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
public class Org {

    private Long id;
    private String name;
    private String legalPerson;
    private String registeredCapital;
    private Boolean hasContact;
    private String employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Boolean getHasContact() {
        return hasContact;
    }

    public void setHasContact(Boolean hasContact) {
        this.hasContact = hasContact;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
