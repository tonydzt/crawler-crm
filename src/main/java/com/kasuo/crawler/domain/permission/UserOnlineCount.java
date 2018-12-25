package com.kasuo.crawler.domain.permission;

import com.kasuo.crawler.domain.core.GenericEntity;


public class UserOnlineCount
        extends GenericEntity {
    private static final long serialVersionUID = 1L;

    public UserOnlineCount() {
    }

    protected int crmOnline = 0;
    protected int appOnline = 0;

    public int getCrmOnline() {
        return crmOnline;
    }

    public void setCrmOnline(int crmOnline) {
        this.crmOnline = crmOnline;
    }

    public int getAppOnline() {
        return appOnline;
    }

    public void setAppOnline(int appOnline) {
        this.appOnline = appOnline;
    }
}
