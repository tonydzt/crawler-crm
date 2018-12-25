package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class AgentFlag extends DataDictionary<AgentFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOAGENT = "0";
    public static final String AGENT = "1";

    public AgentFlag() {
    }

    public AgentFlag(String typeid) {
        setId(typeid);
    }

    public AgentFlag(Item item) {
        load(item);
    }

    public boolean isAgent() {
        return isType("1");
    }
}
