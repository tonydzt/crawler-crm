package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceAnnounceType extends DataDictionary<TMSourceAnnounceType> {
    private static final long serialVersionUID = 1L;
    public static final String REVIEW_NORMAL = "TMZCSQ";
    public static final String REVIEW_UNION = "TMJTSQ";
    public static final String REVIEW_PROVE = "TMZMSQ";
    public static final String APPROVE_NORMAL1 = "TMZCZC";
    public static final String APPROVE_NORMAL2 = "TMQTZC";
    public static final String APPROVE_UNION = "TMJTZC";
    public static final String APPROVE_PROVE = "TMZMZC";
    public static final String TRANSFER = "TMZRSQ";
    public static final String CHANGE_INFO = "TMBMSQ";
    public static final String CHANGE_AGENT = "TMBGSQ";

    public TMSourceAnnounceType() {
    }

    public TMSourceAnnounceType(String typeid) {
        setId(typeid);
    }

    public static final String CORRECT = "TMGZSQ";

    public TMSourceAnnounceType(Item item) {
        load(item);
    }

    public static final String EXTENSION = "TMXZSQ";
    public static final String PERMIT = "TMXKSQ";
    public static final String PLEDGE = "TMZYSQ";
    public static final String CANCEL = "TMZCZX";
    public static final String CANCEL_FOR_NO_EXTENSION = "TMXZZX";
    public static final String REVOKED = "TMCXSQ";
    public static final String INVALIDATION = "TMXGWX";
    public static final String APPLY_WITHDRAW = "TMZCCH";
    public static final String INVALIDATION_ANN = "TMWXGG";
    public static final String SENDED = "TMSDGG";
}
