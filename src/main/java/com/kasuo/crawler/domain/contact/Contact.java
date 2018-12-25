package com.kasuo.crawler.domain.contact;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.ValidFlag;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.source.SourceUtil;

public class Contact extends GenericEntity {
    protected transient Org org = null;
    protected String areacode = null;
    protected String tel = null;
    protected String email = null;
    protected ValidFlag status = null;

    public Contact() {
    }

    public Contact(String name) {
        this.name = name;
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

    public ValidFlag getStatus() {
        return status;
    }

    public void setStatus(ValidFlag status) {
        this.status = status;
    }

}
