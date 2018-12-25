package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class BusiStatus extends DataDictionary<BusiStatus> {
    private static final long serialVersionUID = 1L;
    public static final String CHITM_REGISTER_INIT = "100000";
    public static final String CHITM_REGISTER_WAIT_INCOME_CONFIRM = "100001";
    public static final String CHITM_REGISTER_INCOME_ARRIVED = "100002";
    public static final String CHITM_REGISTER_WAIT_INFO_CHECK = "100003";
    public static final String CHITM_REGISTER_INFO_CHECKED = "100004";
    public static final String CHITM_REGISTER_WAIT_SUBMIT = "100005";

    public BusiStatus() {
    }

    public BusiStatus(String typeid) {
        setId(typeid);
    }

    public BusiStatus(Item item) {
        load(item);
    }


    public static final String CHITM_REGISTER_SUBMITTED = "100006";


    public static final String CHITM_REGISTER_WAIT_ACCEPT_DISPATCH = "100007";


    public static final String CHITM_REGISTER_ACCEPT_ARRIVED = "100008";


    public static final String CHITM_REGISTER_WAIT_REVIEW_NOTICE = "100009";


    public static final String CHITM_REGISTER_REVIEW_NOTICED = "100010";


    public static final String CHITM_REGISTER_WAIT_APPROVE_NOTICE = "100011";


    public static final String CHITM_REGISTER_APPROVE_NOTICED = "100012";


    public static final String CHITM_REGISTER_WAIT_APPROVE_PAPER = "100013";


    public static final String CHITM_REGISTER_APPROVE_PAPER_ARRIVED = "100014";


    public static final String CHITM_REJECTED_INIT = "100100";


    public static final String CHITM_REJECTED_WAIT_INCOME_CONFIRM = "100101";


    public static final String CHITM_REJECTED_INCOME_ARRIVED = "100102";


    public static final String CHITM_REJECTED_WAIT_INFO_CHECK = "100103";


    public static final String CHITM_REJECTED_INFO_CHECKED = "100104";


    public static final String CHITM_REJECTED_WAIT_APPLY_SEND = "100105";


    public static final String CHITM_REJECTED_APPLY_SENDED = "100106";


    public static final String CHITM_REJECTED_WAIT_ACCEPT_DISPATCH = "100107";


    public static final String CHITM_REJECTED_ACCEPT_ARRIVED = "100108";


    public static final String CHITM_REJECTED_WAIT_ADDITION_SEND = "100109";


    public static final String CHITM_REJECTED_ADDITION_SENDED = "100110";


    public static final String CHITM_REJECTED_WAIT_CONSULTATION = "100111";


    public static final String CHITM_REJECTED_CONSULTATION_ARRIVED = "100112";


    public boolean isChiTMInit() {
        return isType("100000");
    }


    public boolean isChiTMWaitIncomeConfirm() {
        return isType("100001");
    }


    public boolean isChiTMIncomeArrived() {
        return isType("100002");
    }


    public boolean isChiTMWaitInfoCheck() {
        return isType("100003");
    }


    public boolean isChiTMInfoChecked() {
        return isType("100004");
    }


    public boolean isChiTMWaitSubmit() {
        return isType("100005");
    }


    public boolean isChiTMSubmitted() {
        return isType("100006");
    }


    public boolean isChiTMWaitAcceptDispatch() {
        return isType("100007");
    }


    public boolean isChiTMAcceptArrived() {
        return isType("100008");
    }


    public boolean isChiTMWaitReviewNotice() {
        return isType("100009");
    }


    public boolean isChiTMReviewNoticed() {
        return isType("100010");
    }


    public boolean isChiTMWaitApproveNotice() {
        return isType("100011");
    }


    public boolean isChiTMApproveNoticed() {
        return isType("100012");
    }


    public boolean isChiTMWaitApprovePaper() {
        return isType("100013");
    }


    public boolean isChiTMApprovePaperArrived() {
        return isType("100014");
    }
}
