package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMBusiStep extends DataDictionary<TMBusiStep> {
    private static final long serialVersionUID = 1L;
    public static final String REG_APPLY_RECEIPT = "01";
    public static final String REG_WAIT_ACCEPT_DISPATCH = "02";
    public static final String REG_WAIT_REJECT_DISPATCH = "03";
    public static final String REG_WAIT_REJECT_RETRIAL = "04";
    public static final String REG_WAIT_REGISTER_DISPATCH = "05";
    public static final String REG_ACCEPT_RETURN = "06";

    public TMBusiStep() {
    }

    public TMBusiStep(String typeid) {
        setId(typeid);
    }

    public TMBusiStep(Item item) {
        load(item);
    }


    public static final String REG_ACCEPT_SENDED_NOTICE = "07";


    public static final String REG_ACCEPT_RETURN_RECEIVE = "08";


    public static final String REG_ACCEPT_PRINT = "09";


    public static final String REG_WAIT_CORRECT_DISPATCH = "0a";


    public static final String REG_WAIT_CORRECT_RETURN = "0b";


    public static final String REG_CORRECT_RETURN = "0c";


    public static final String REG_CORRECT_SENDED_NOTICE = "0d";


    public static final String REG_CORRECT_RETURN_RECEIVE = "0e";


    public static final String REG_REJECT_PRINT = "0f";


    public static final String REJ_APPLY_RECEIPT = "11";


    public static final String REJ_REVIEW = "12";


    public static final String REJ_WAIT_JUDGE_DISPATCH = "13";


    public static final String REJ_WAIT_PRINT_REGISTER = "14";


    public static final String PRI_APPLY_RECEIPT = "a1";


    public static final String PRI_WAIT_JUDGE_DISPATCH = "a2";


    public static final String INT_APPLY_RECEIPT = "b1";


    public static final String TRA_APPLY_RECEIPT = "81";


    public static final String CHA_APPLY_RECEIPT = "91";


    public static final String CHA_WAIT_ACCEPT_PRINT = "92";


    public static final String CHA_JUDGE_PRINT = "93";


    public static final String OTHER = "n";


    public boolean isRegApplyReceipt() {
        return isType("01");
    }


    public boolean isRegWaitAcceptDispatch() {
        return isType("02");
    }


    public boolean isRegWaitRejectDispatch() {
        return isType("03");
    }


    public boolean isRegWaitRejectRetrial() {
        return isType("04");
    }


    public boolean isRegWaitRegisterDispatch() {
        return isType("05");
    }


    public boolean isRegAcceptReturn() {
        return isType("06");
    }


    public boolean isRegAcceptSendedNotice() {
        return isType("07");
    }


    public boolean isRegAcceptReturnReceive() {
        return isType("08");
    }


    public boolean isRegAcceptPrint() {
        return isType("09");
    }


    public boolean isRegWaitCorrectDispatch() {
        return isType("0a");
    }


    public boolean isRegWaitCorrectReturn() {
        return isType("0b");
    }


    public boolean isRegCorrectReturn() {
        return isType("0c");
    }


    public boolean isRegCorrectSendedNotice() {
        return isType("0d");
    }


    public boolean isRegCorrectReturnReceive() {
        return isType("0e");
    }


    public boolean isRegRejectPrint() {
        return isType("0f");
    }


    public boolean isRejApplyReceipt() {
        return isType("11");
    }


    public boolean isRejReview() {
        return isType("12");
    }


    public boolean isRejWaitJudgeDispatch() {
        return isType("13");
    }


    public boolean isRejWaitPrintRegister() {
        return isType("14");
    }


    public boolean isPriApplyReceipt() {
        return isType("a1");
    }


    public boolean isPriWaitJudgeDispatch() {
        return isType("a2");
    }


    public boolean isIntApplyReceipt() {
        return isType("b1");
    }


    public boolean isTraApplyReceipt() {
        return isType("81");
    }


    public boolean isChaApplyReceipt() {
        return isType("91");
    }


    public boolean isChaWaitAcceptPrint() {
        return isType("92");
    }


    public boolean isChaJudgePrint() {
        return isType("93");
    }


    public boolean isOther() {
        return isType("n");
    }
}
