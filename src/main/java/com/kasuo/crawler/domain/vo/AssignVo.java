package com.kasuo.crawler.domain.vo;

/**
 * @author douzhitong
 * @date 2019/2/17
 */
public class AssignVo {

    private String date;
    private Integer num;
    private Integer mobileNum;
    private Integer telNum;
    private Integer totalNum;
    private Integer assignNum;
    private Integer unassignNum;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(Integer mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Integer getTelNum() {
        return telNum;
    }

    public void setTelNum(Integer telNum) {
        this.telNum = telNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(Integer assignNum) {
        this.assignNum = assignNum;
    }

    public Integer getUnassignNum() {
        return unassignNum;
    }

    public void setUnassignNum(Integer unassignNum) {
        this.unassignNum = unassignNum;
    }
}
