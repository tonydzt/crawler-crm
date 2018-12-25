package com.kasuo.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author douzhitong
 * @date 18/11/15
 */
@Component
@ConfigurationProperties(prefix = "crawler")
public class CrawlerConfig {

    private Integer type;
    private String user;
    private String passward;
    private Integer iniWait;
    private Integer incWait;
    private Integer basicTimeout;
    private Integer detailTimeout;
    private Integer maxRecords;
    private Integer startHour;
    private Integer endHour;
    private Integer startVerify;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public Integer getIniWait() {
        return iniWait;
    }

    public void setIniWait(Integer iniWait) {
        this.iniWait = iniWait;
    }

    public Integer getIncWait() {
        return incWait;
    }

    public void setIncWait(Integer incWait) {
        this.incWait = incWait;
    }

    public Integer getBasicTimeout() {
        return basicTimeout;
    }

    public void setBasicTimeout(Integer basicTimeout) {
        this.basicTimeout = basicTimeout;
    }

    public Integer getDetailTimeout() {
        return detailTimeout;
    }

    public void setDetailTimeout(Integer detailTimeout) {
        this.detailTimeout = detailTimeout;
    }

    public Integer getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getStartVerify() {
        return startVerify;
    }

    public void setStartVerify(Integer startVerify) {
        this.startVerify = startVerify;
    }

    @Override
    public String toString() {
        return "CrawlerConfig{" +
                "type=" + type +
                ", user='" + user + '\'' +
                ", passward='" + passward + '\'' +
                ", iniWait=" + iniWait +
                ", incWait=" + incWait +
                ", basicTimeout=" + basicTimeout +
                ", detailTimeout=" + detailTimeout +
                ", maxRecords=" + maxRecords +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", startVerify=" + startVerify +
                '}';
    }
}
