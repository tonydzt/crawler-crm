package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class BusiSubType extends DataDictionary<BusiSubType> {
    private static final long serialVersionUID = 1L;
    public static final String CHITM_REGISTER = "1000";
    public static final String CHITM_REJECTED_RETRIAL = "1001";
    public static final String CHITM_OBJECTION = "1002";
    public static final String CHITM_OBJECTION_REPLY = "1003";
    public static final String CHITM_CANCEL_FOR_NOUSE = "1004";
    public static final String CHITM_CANCEL_FOR_NOUSE_REPLY = "1005";
    public static final String CHITM_INVALIDATION = "1006";
    public static final String CHITM_INVALIDATION_REPLY = "1007";
    public static final String CHITM_TRANSFER = "1008";
    public static final String CHITM_CHANGE_REGISTER_INFO = "1009";
    public static final String CHITM_PRIORITY_APPLY = "1010";
    public static final String CHITM_REGISTER_INTERNATION = "1011";
    public static final String CHITM_EXTENSION = "1012";
    public static final String CHITM_OTHER = "1099";

    public BusiSubType() {
    }

    public BusiSubType(String typeid) {
        setId(typeid);
    }

    public BusiSubType(Item item) {
        load(item);
    }


    public boolean isChiTMRegister() {
        return isType("1000");
    }


    public boolean isChiTMRejectedRetrial() {
        return isType("1001");
    }


    public boolean isChiTMObjection() {
        return isType("1002");
    }


    public boolean isChiTMObjectionReply() {
        return isType("1003");
    }


    public boolean isChiTMCancelForNouse() {
        return isType("1004");
    }


    public boolean isChiTMCancelForNouseReply() {
        return isType("1005");
    }


    public boolean isChiTMInvalidation() {
        return isType("1006");
    }


    public boolean isChiTMInvalidationReply() {
        return isType("1007");
    }


    public boolean isChiTMTransfer() {
        return isType("1008");
    }


    public boolean isChiTMChangeRegisterInfo() {
        return isType("1009");
    }


    public boolean isChiTMPriorityApply() {
        return isType("1010");
    }


    public boolean isChiTMRegisterInternation() {
        return isType("1011");
    }


    public boolean isChiTMExtension() {
        return isType("1012");
    }


    public boolean isChiTMOther() {
        return isType("1099");
    }
}
