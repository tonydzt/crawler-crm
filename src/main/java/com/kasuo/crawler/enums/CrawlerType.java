package com.kasuo.crawler.enums;

/**
 * @author douzhitong
 * @date 18/11/15
 */
public enum  CrawlerType {

    QI_XIN_BAO("启信宝"),
    TIAN_YAN_CHA("天眼查"),
    QI_CHA_CHA("企查查"),
    QI_CHA_MAO("企查猫");

    CrawlerType (String crawlerName) {
        this.crawlerName = crawlerName;
    }

    private String crawlerName;

    public String getCrawlerName() {
        return crawlerName;
    }

    public void setCrawlerName(String crawlerName) {
        this.crawlerName = crawlerName;
    }
}
