package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.util.DateTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Org
        extends GenericEntity {
    protected String orgName = null;
    protected Set<OrgHisName> oldNames = new HashSet();
    protected List<Contact> contacts = new ArrayList();
    protected String address = null;
    protected String tmAddress = null;
    protected String uscd = null;
    protected String orgCode = null;
    protected String busiLicenseCode = null;
    protected String status = null;
    protected OrgType orgType = null;
    protected String industryType = null;
    protected Date regDate = null;
    protected String principal = null;
    protected int regCapital = 0;
    protected CurrencyType capitalUnit = null;
    protected String regOrganization = null;
    protected String busiScope = null;
    protected String web = null;
    protected CrawlFlag crawlFlag = new CrawlFlag("0");
    private String legalPerson;
    private String registeredCapital;

    public Org() {
    }

    public Org(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id + ",orgName=" + orgName + ",createTime=" + DateTool.getStringDate(createTime);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Set<OrgHisName> getOldNames() {
        return oldNames;
    }

    public void setOldNames(Set<OrgHisName> oldNames) {
        this.oldNames = oldNames;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTmAddress(String tmAddress) {
        this.tmAddress = tmAddress;
    }

    public String getUscd() {
        return uscd;
    }

    public void setUscd(String uscd) {
        this.uscd = uscd;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBusiLicenseCode() {
        return busiLicenseCode;
    }

    public void setBusiLicenseCode(String busiLicenseCode) {
        this.busiLicenseCode = busiLicenseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public int getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(int regCapital) {
        this.regCapital = regCapital;
    }

    public CurrencyType getCapitalUnit() {
        return capitalUnit;
    }

    public void setCapitalUnit(CurrencyType capitalUnit) {
        this.capitalUnit = capitalUnit;
    }

    public String getRegOrganization() {
        return regOrganization;
    }

    public void setRegOrganization(String regOrganization) {
        this.regOrganization = regOrganization;
    }

    public String getBusiScope() {
        return busiScope;
    }

    public void setBusiScope(String busiScope) {
        this.busiScope = busiScope;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public CrawlFlag getCrawlFlag() {
        return crawlFlag;
    }

    public void setCrawlFlag(CrawlFlag crawlFlag) {
        this.crawlFlag = crawlFlag;
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
}
