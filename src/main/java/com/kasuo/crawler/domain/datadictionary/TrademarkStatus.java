package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

import java.util.Date;


public class TrademarkStatus
        extends DataDictionary<TrademarkStatus> {
    private static final long serialVersionUID = 1L;
    private Date busiDate = null;
    public static final String REVIEWING = "1";
    public static final String REVIEWED = "2";

    public TrademarkStatus() {
    }

    public TrademarkStatus(String typeid) {
        setId(typeid);
    }

    public TrademarkStatus(Item item) {
        load(item);
    }


    public static final String REJECTED = "3";


    public static final String APPROVED = "4";


    public static final String CANCEL = "5";


    public static final String REJECTED_RETRIAL = "6";


    public static final String RETRIAL_REJECTED = "7";


    public static final String UNKNOWN = "0";


    public boolean isReviewing() {
        return isType("1");
    }


    public boolean isReviewed() {
        return isType("2");
    }


    public boolean isRejected() {
        return isType("3");
    }


    public boolean isApproved() {
        return isType("4");
    }


    public boolean isCancel() {
        return isType("5");
    }


    public boolean isRejectedRetrial() {
        return isType("6");
    }


    public boolean isRetrialRejected() {
        return isType("7");
    }


    public boolean isUnknown() {
        return isType("0");
    }

    public Date getBusiDate() {
        return busiDate;
    }

    public void setBusiDate(Date busiDate) {
        this.busiDate = busiDate;
    }
}
