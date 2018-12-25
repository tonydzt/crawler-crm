package com.kasuo.crawler.domain.trademark.entity;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.TMBusiStep;
import com.kasuo.crawler.domain.datadictionary.TMBusiType;

import java.util.Date;

public class TrademarkProcess
        extends GenericEntity {
    private static final long serialVersionUID = 1L;
    private Trademark tm = null;
    private String applyNum = null;
    private Date busiDate = null;
    private TMBusiType busiType = null;
    private String busiTypeDesc = null;
    private TMBusiStep busiStep = null;
    private String busiStepDesc = null;

    private String regNum = null;
    private int cls = 0;

    public TrademarkProcess() {
    }

    public boolean isApplyAccept() {
        if ((busiType != null) && (busiType.isRegister()) && (busiStep != null) && (busiStep.isRegWaitAcceptDispatch())) {
            return true;
        }
        return false;
    }


    public Trademark getTm() {
        return tm;
    }

    public void setTm(Trademark tm) {
        this.tm = tm;
    }

    public Date getBusiDate() {
        return busiDate;
    }

    public void setBusiDate(Date busiDate) {
        this.busiDate = busiDate;
    }

    public TMBusiType getBusiType() {
        return busiType;
    }

    public void setBusiType(TMBusiType busiType) {
        this.busiType = busiType;
    }

    public String getBusiTypeDesc() {
        return busiTypeDesc;
    }

    public void setBusiTypeDesc(String busiTypeDesc) {
        this.busiTypeDesc = busiTypeDesc;
    }

    public TMBusiStep getBusiStep() {
        return busiStep;
    }

    public void setBusiStep(TMBusiStep busiStep) {
        this.busiStep = busiStep;
    }

    public String getBusiStepDesc() {
        return busiStepDesc;
    }

    public void setBusiStepDesc(String busiStepDesc) {
        this.busiStepDesc = busiStepDesc;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }
}
