package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.contact.ContactBook;
import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.domain.permission.User;
import com.kasuo.crawler.domain.source.SourceUtil;
import com.kasuo.crawler.util.CrmTool;
import com.kasuo.crawler.util.DateTool;
import com.kasuo.crawler.util.StringTool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Org
        extends GenericEntity {
    public static final String UNKNOWN_ID = "0005612699e347e6bd4b4e3e074b0d2b";
    private static final long serialVersionUID = 1L;
    protected String orgName = null;
    protected Set<OrgHisName> oldNames = new HashSet();
    protected List<Contact> contacts = new ArrayList();
    protected String workTel1 = null;
    protected String workTel2 = null;
    protected String address = null;
    protected String tmAddress = null;
    protected int addrDistrict = 0;
    protected String addrProvince = null;
    protected String addrCity = null;
    protected String postcode = null;
    protected String areacode = null;
    protected String uscd = null;
    protected String orgCode = null;
    protected String busiLicenseCode = null;
    protected String status = null;
    protected OrgType orgType = null;
    protected String industryType = null;
    protected Date regDate = null;
    protected String regDateShow = null;
    protected String principal = null;
    protected int regCapital = 0;
    protected int realCapital = 0;
    protected String regCapitalShow = null;
    protected CurrencyType capitalUnit = null;
    protected String regOrganization = null;
    protected String busiScope = null;
    protected String web = null;
    protected String tags = null;
    protected User createUser = null;
    protected User lastModifyUser = null;
    protected User assignedUser = null;
    protected CUTurnoverFlag turnover = new CUTurnoverFlag("0");
    protected AgentFlag agentFlag = new AgentFlag("0");
    protected String memo = null;
    protected SysInfo warn = null;
    protected CrawlerType crawlerType = null;
    protected String crawlerUser = null;
    protected String crawlerIp = null;
    protected Date crawlTime = null;
    protected OwnerFlag ownerFlag = null;
    protected CrawlFlag crawlFlag = new CrawlFlag("0");
    protected int crawlCount = 0;
    protected Date contactTime = null;
    protected User contactUser = null;
    protected ContactStatus contactStatus = null;
    protected Date assignedTime = null;
    protected int validPhoneNums = 0;
    protected int validTelNums = 0;

    protected Priority crawlPriority = new Priority("1");
    protected BusiPurpose purpose = new BusiPurpose("2");
    protected Date purposeTime = null;
    protected Date nextContactTime = null;
    protected Date nextContactHour = null;
    protected Priority priority = new Priority("1");
    protected String paper = null;
    protected String seal = null;
    protected SysHint sysHint = null;
    protected Integer groupNum = null;
    protected int groupCount = 0;
    protected List<Org> groups = null;
    private String legalPerson;
    private String registeredCapital;

    public Org() {
    }

    public Org(String id) {
        this.id = id;
    }


    public void addContact(String contactName, String book, String type, String memo, String... areacode) {
        Contact contact = getContact(contactName);

        if (type.equals("1")) {
            if ((contact.getTel() != null) && (!contact.getTel().equals(book))) {
                contact = addNewContact(contactName);
            }
            contact.setTel(book);
        } else if (type.equals("3")) {
            if ((contact.getWx() != null) && (!contact.getWx().equals(book))) {
                contact = addNewContact(contactName);
            }
            contact.setWx(book);
        } else if (type.equals("2")) {
            if ((contact.getQq() != null) && (!contact.getQq().equals(book))) {
                contact = addNewContact(contactName);
            }
            contact.setQq(book);
        } else if (type.equals("4")) {
            if ((contact.getEmail() != null) && (!contact.getEmail().equals(book))) {
                contact = addNewContact(contactName);
            }
            contact.setEmail(book);
        }

        if ((memo != null) && (!memo.trim().equals(""))) {
            contact.setMemo(memo);
        }
        if (areacode.length > 0) {
            this.areacode = areacode[0];
            contact.setAreacode(areacode[0]);
        }
    }


    public void addBook(String contactName, String book, String type, String memo, String... areacode) {
        Contact contact = getContact(contactName);
        Set<ContactBook> books = contact.getBooks();

        if (books.size() == 0) {
            ContactBook cb = new ContactBook(book, type, memo, areacode);
            cb.setContact(contact);
            books.add(cb);
        } else {
            boolean bFound = false;
            for (ContactBook b : books) {
                if ((b.getType().isType(type)) && (b.getBook().contains(book.trim()))) {

                    bFound = true;
                    break;
                }
            }

            if (!bFound) {
                ContactBook cb = new ContactBook(book, type, memo, areacode);
                cb.setContact(contact);
                books.add(cb);
            }
        }


        CrmTool.setContactDefaultTel(contact);
    }


    public void mergeContactAndBooks(Contact bean) {
        Contact contact = getContact(bean.getName());

        if (contact.getRole() == null) {
            contact.setRole(bean.getRole());
        }
        if (contact.getStatus() == null) {
            contact.setStatus(bean.getStatus());
        }
        if ((contact.getAreacode() == null) || (contact.getAreacode().trim().equals(""))) {
            contact.setAreacode(bean.getAreacode());
        }
        if ((contact.getTel() == null) || (contact.getTel().trim().equals(""))) {
            contact.setTel(bean.getTel());
        } else if (SourceUtil.isPhoneNum(bean.getTel())) {
            contact.setTel(bean.getTel());
        }
        if ((contact.getQq() == null) || (contact.getQq().trim().equals(""))) {
            contact.setQq(bean.getQq());
        }
        if ((contact.getWx() == null) || (contact.getWx().trim().equals(""))) {
            contact.setWx(bean.getWx());
        }
        if ((contact.getEmail() == null) || (contact.getEmail().trim().equals(""))) {
            contact.setEmail(bean.getEmail());
        }

        for (ContactBook cb : bean.getBooks()) {
            boolean bFound = false;
            for (ContactBook b : contact.getBooks()) {
                if ((b.getType().isType(cb.getType().getId())) && (b.getBook().contains(cb.getBook()))) {

                    bFound = true;
                    break;
                }
            }

            if (!bFound) {
                contact.getBooks().add(cb);
            }
        }


        CrmTool.setContactDefaultTel(contact);
    }


    public Contact getContact(String contactName) {
        Contact contact;


        if (contacts.size() == 0) {
            contact = new Contact(contactName);
            contact.setOrg(this);
            contacts.add(contact);
        } else {
            for (Contact c : contacts) {
                if (c.getName().equals(contactName)) {
                    contact = c;
                    return contact;
                }
            }

            contact = new Contact(contactName);
            contact.setOrg(this);
            contacts.add(contact);
        }
        return contact;
    }

    public Contact addNewContact(String contactName) {
        Contact contact = new Contact(contactName);
        contact.setOrg(this);
        contacts.add(contact);
        return contact;
    }

    public String getRegCapitalShow() {
        String ret = "-";
        if (regCapital != 0) {
            ret = regCapital + "万";
            if ((capitalUnit != null) && (capitalUnit.getName() != null)) {
                ret = ret + capitalUnit.getName();
            } else {
                ret = ret + "人民币";
            }
        }
        return ret;
    }

    public void setRegCapitalShow(String regCapitalShow) {
        this.regCapitalShow = regCapitalShow;
    }

    public String getRegDateShow() {
        String ret = "-";
        if (regDate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ret = sdf.format(regDate);
            } catch (Exception localException) {
            }
        }

        return ret;
    }

    public void setRegDateShow(String regDateShow) {
        this.regDateShow = regDateShow;
    }

    @Override
    public String toString() {
        return "id=" + id + ",orgName=" + orgName + ",createTime=" + DateTool.getStringDate(createTime);
    }


    public boolean isAddressSimilar() {
        return StringTool.isSimilar(address, tmAddress);
    }


    public String getTmAddress() {
        if ((tmAddress != null) && (!tmAddress.trim().equals(""))) {
            return tmAddress;
        }
        return address;
    }


    public OwnerFlag getOwnerFlag() {
        return ownerFlag;
    }

    public void setOwnerFlag(OwnerFlag ownerFlag) {
        this.ownerFlag = ownerFlag;
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

    public AgentFlag getAgentFlag() {
        return agentFlag;
    }

    public void setAgentFlag(AgentFlag agentFlag) {
        this.agentFlag = agentFlag;
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

    public String getWorkTel1() {
        return workTel1;
    }

    public void setWorkTel1(String workTel1) {
        this.workTel1 = workTel1;
    }

    public String getWorkTel2() {
        return workTel2;
    }

    public void setWorkTel2(String workTel2) {
        this.workTel2 = workTel2;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
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

    public int getAddrDistrict() {
        return addrDistrict;
    }

    public void setAddrDistrict(int addrDistrict) {
        this.addrDistrict = addrDistrict;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public OrgType getOrgType() {
        return orgType;
    }

    public String getIndustryType() {
        return industryType;
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

    public int getRealCapital() {
        return realCapital;
    }

    public void setRealCapital(int realCapital) {
        this.realCapital = realCapital;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(User lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public CUTurnoverFlag getTurnover() {
        return turnover;
    }

    public void setTurnover(CUTurnoverFlag turnover) {
        this.turnover = turnover;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public CrawlerType getCrawlerType() {
        return crawlerType;
    }

    public void setCrawlerType(CrawlerType crawlerType) {
        this.crawlerType = crawlerType;
    }

    public String getCrawlerUser() {
        return crawlerUser;
    }

    public void setCrawlerUser(String crawlerUser) {
        this.crawlerUser = crawlerUser;
    }

    public String getCrawlerIp() {
        return crawlerIp;
    }

    public void setCrawlerIp(String crawlerIp) {
        this.crawlerIp = crawlerIp;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

    public CrawlFlag getCrawlFlag() {
        return crawlFlag;
    }

    public void setCrawlFlag(CrawlFlag crawlFlag) {
        this.crawlFlag = crawlFlag;
    }

    public Date getContactTime() {
        return contactTime;
    }

    public void setContactTime(Date contactTime) {
        this.contactTime = contactTime;
    }

    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }

    public ContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    public Date getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Date assignedTime) {
        this.assignedTime = assignedTime;
    }

    public int getCrawlCount() {
        return crawlCount;
    }

    public void setCrawlCount(int crawlCount) {
        this.crawlCount = crawlCount;
    }

    public SysInfo getWarn() {
        return warn;
    }

    public void setWarn(SysInfo warn) {
        this.warn = warn;
    }

    public int getValidTelNums() {
        return validTelNums;
    }

    public void setValidTelNums(int validTelNums) {
        this.validTelNums = validTelNums;
    }

    public int getValidPhoneNums() {
        return validPhoneNums;
    }

    public void setValidPhoneNums(int validPhoneNums) {
        this.validPhoneNums = validPhoneNums;
    }

    public Priority getCrawlPriority() {
        return crawlPriority;
    }

    public void setCrawlPriority(Priority crawlPriority) {
        this.crawlPriority = crawlPriority;
    }

    public BusiPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(BusiPurpose purpose) {
        this.purpose = purpose;
    }

    public Date getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(Date nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getPurposeTime() {
        return purposeTime;
    }

    public void setPurposeTime(Date purposeTime) {
        this.purposeTime = purposeTime;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public SysHint getSysHint() {
        return sysHint;
    }

    public void setSysHint(SysHint sysHint) {
        this.sysHint = sysHint;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Date getNextContactHour() {
        return nextContactHour;
    }

    public void setNextContactHour(Date nextContactHour) {
        this.nextContactHour = nextContactHour;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public List<Org> getGroups() {
        return groups;
    }

    public void setGroups(List<Org> groups) {
        this.groups = groups;
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
