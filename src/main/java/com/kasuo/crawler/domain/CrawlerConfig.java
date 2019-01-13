package com.kasuo.crawler.domain;

/**
 * @author douzhitong
 * @date 2019/1/7
 */
public class CrawlerConfig {

    public static final String CRAWLER_DATE = "crawler_date";
    public static final String CRAWLER_NUM = "crawler_num";

    private Long id;
    private String key;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
