package com.kasuo.crawler.domain.contact;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.ContactRole;
import com.kasuo.crawler.domain.datadictionary.Gender;
import com.kasuo.crawler.domain.datadictionary.ValidFlag;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.permission.User;
import com.kasuo.crawler.domain.source.SourceUtil;

import java.util.HashSet;
import java.util.Set;

public class Contact extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected transient Org org = null;
    protected ContactRole role = null;
    protected Gender gender = null;
    protected String position = null;
    protected String areacode = null;
    protected String tel = null;
    protected String telShow = null;
    protected String qq = null;
    protected String wx = null;
    protected String email = null;
    protected String memo = null;
    protected ValidFlag status = null;
    protected User createUser = null;
    protected User lastModifyUser = null;
    protected Set<ContactBook> books = new HashSet();


    public Contact() {
    }


    public Contact(String name) {
        this.name = name;
    }

    public String getTelShow() {
        if (tel != null) {
            return patchSpace2Tel(tel);
        }
        return null;
    }

    private String patchSpace2Tel(String s) {
        String ret = null;

        if (SourceUtil.isPhoneNum(s)) {
            ret = s.substring(0, 3) + "-" + s.substring(3, 7) + "-" + s.substring(7);
        } else if (areacode != null) {
            if ((s.startsWith(areacode)) && (s.length() - areacode.length() > 5)) {
                int i = areacode.length();
                ret = s.substring(0, i) + " " + s.substring(i, i + 4) + "-" + s.substring(i + 4);
            } else {
                ret = patchSplit2Tel(s);
            }
        } else {
            ret = patchSplit2Tel(s);
        }


        return ret;
    }

    private String patchSplit2Tel(String s) {
        String ret = null;
        if (s.length() > 4) {
            ret = s.substring(0, 4) + "-" + patchSpace2Tel(s.substring(4));
        } else {
            ret = s;
        }

        return ret;
    }

    public Set<ContactBook> getTels() {
        Set<ContactBook> ret = null;
        if (books != null) {
            ret = new HashSet();
            for (ContactBook book : books) {
                if (book.getType().isTel()) {
                    ret.add(book);
                }
            }
        }
        return ret;
    }

    public Set<ContactBook> getQQs() {
        Set<ContactBook> ret = null;
        if (books != null) {
            ret = new HashSet();
            for (ContactBook book : books) {
                if (book.getType().isQQ()) {
                    ret.add(book);
                }
            }
        }
        return ret;
    }

    public Set<ContactBook> getWXs() {
        Set<ContactBook> ret = null;
        if (books != null) {
            ret = new HashSet();
            for (ContactBook book : books) {
                if (book.getType().isWX()) {
                    ret.add(book);
                }
            }
        }
        return ret;
    }

    public Set<ContactBook> getMails() {
        Set<ContactBook> ret = null;
        if (books != null) {
            ret = new HashSet();
            for (ContactBook book : books) {
                if (book.getType().isEmail()) {
                    ret.add(book);
                }
            }
        }
        return ret;
    }


    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public ContactRole getRole() {
        return role;
    }

    public void setRole(ContactRole role) {
        this.role = role;
    }

    public Set<ContactBook> getBooks() {
        return books;
    }

    public void setBooks(Set<ContactBook> books) {
        this.books = books;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
