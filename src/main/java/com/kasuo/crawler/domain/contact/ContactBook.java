package com.kasuo.crawler.domain.contact;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.ContactBookType;
import com.kasuo.crawler.domain.datadictionary.ValidFlag;
import com.kasuo.crawler.domain.permission.User;

import java.util.Date;

public class ContactBook
        extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected transient Contact contact = null;
    protected ContactBookType type = new ContactBookType("1");
    protected String book = null;
    protected String areacode = null;
    protected Date lastTime = null;
    protected String memo = null;
    protected ValidFlag status = null;
    protected User createUser = null;
    protected User lastModifyUser = null;


    public ContactBook() {
    }


    public ContactBook(String book, String type, String memo, String... areacode) {
        this.book = book;
        this.type = new ContactBookType(type);
        this.memo = memo;
        if (areacode.length > 0) {
            this.areacode = areacode[0];
        }
    }

    public ContactBookType getType() {
        return type;
    }

    public void setType(ContactBookType type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ValidFlag getStatus() {
        return status;
    }

    public void setStatus(ValidFlag status) {
        this.status = status;
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

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
}
