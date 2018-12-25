package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceMailStatus extends DataDictionary<SourceMailStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String DOWNLOADING = "1";
    public static final String DOWNLOADED = "2";
    public static final String UNZIPING = "3";
    public static final String UNZIPED = "4";
    public static final String VERIFING = "5";
    public static final String VERIFED = "6";
    public static final String PREPARING = "7";
    public static final String PREPARED = "8";

    public SourceMailStatus() {
    }

    public SourceMailStatus(String typeid) {
        setId(typeid);
    }

    public SourceMailStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }

    public boolean isDownloading() {
        return isType("1");
    }

    public boolean isDownloaded() {
        return isType("2");
    }

    public boolean isUnziping() {
        return isType("3");
    }

    public boolean isUnziped() {
        return isType("4");
    }

    public boolean isVerifing() {
        return isType("5");
    }

    public boolean isVerified() {
        return isType("6");
    }

    public boolean isPreparing() {
        return isType("7");
    }

    public boolean isPrepared() {
        return isType("8");
    }
}
