package com.kasuo.crawler.domain.contact;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.org.Org;

public class Contact extends GenericEntity {
    protected transient Org org = null;
    protected String tel = null;
    protected String email = null;

    public Contact(String name) {
        this.name = name;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
