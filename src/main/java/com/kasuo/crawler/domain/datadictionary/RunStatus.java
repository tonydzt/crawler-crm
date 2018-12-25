package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class RunStatus extends DataDictionary<RunStatus> {
    private static final long serialVersionUID = 1L;
    public static final String STOP = "0";
    public static final String RUNNING = "1";
    public static final String RUNNING_WARN = "2";
    public static final String STOP_FAIL = "3";

    public RunStatus() {
    }

    public RunStatus(String typeid) {
        setId(typeid);
    }

    public RunStatus(Item item) {
        load(item);
    }


    public boolean isStop() {
        return isType("0");
    }

    public boolean isRunning() {
        return isType("1");
    }

    public boolean isStopFail() {
        return isType("3");
    }

    public boolean isRunningWarn() {
        return isType("2");
    }

    public boolean isStopStatus() {
        return (isType("0")) || (isType("3"));
    }

    public boolean isRunningStatus() {
        return (isType("1")) || (isType("2"));
    }
}
