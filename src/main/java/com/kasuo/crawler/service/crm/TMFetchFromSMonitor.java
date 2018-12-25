package com.kasuo.crawler.service.crm;

import java.util.Map;

import com.kasuo.crawler.domain.datadictionary.CrawlOrgFlag;
import com.kasuo.crawler.domain.datadictionary.ValidFlag;
import com.kasuo.crawler.domain.source.SourceInput;
import com.kasuo.crawler.domain.source.SourceInputTMDetail;
import com.kasuo.crawler.domain.source.SourceUtil;
import com.kasuo.crawler.domain.trademark.entity.Trademark;
import com.kasuo.crawler.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMFetchFromSMonitor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public static void mergeInputTMDetail(Map<String, SourceInput> inputMap, SourceInputTMDetail detail) {
        try {
            SourceInput existInput = null;
            Trademark lastApplyTm = detail.getTm();

            String orgName = detail.getOrgName();
            String tmAddress = detail.getTmAddress();
            logger.debug("mergeInputTMDetail: orgName=" + orgName + ",trAddress=" + tmAddress);
            if (orgName != null) {
                existInput = (SourceInput) inputMap.get(orgName);
                if (existInput != null) {
                    for (SourceInputTMDetail existDetail : existInput.getDetails()) {
                        Trademark existTm = existDetail.getTm();
                        if ((lastApplyTm != null) && (existTm != null) && (!compareTMApplyDate(lastApplyTm, existTm))) {
                            lastApplyTm = existTm;
                        }
                    }
                    if (lastApplyTm != null) {
                        tmAddress = lastApplyTm.getTmAddress();
                    }
                    existInput.setTmAddress(tmAddress);
                } else {
                    existInput = new SourceInput();
                    existInput.setOrgName(orgName);
                    existInput.setTmAddress(tmAddress);
                }
                if (SourceUtil.checkValidSourceOrgName(orgName, tmAddress)) {
                    existInput.setValidFlag(new ValidFlag("1"));
                    existInput.setCrawlOrgFlag(new CrawlOrgFlag("1"));
                } else {
                    existInput.setValidFlag(new ValidFlag("0"));
                    existInput.setCrawlOrgFlag(new CrawlOrgFlag("0"));
                }
            } else {
                existInput = new SourceInput();

                orgName = detail.getTm().getRegNum() + "_" + detail.getTm().getCls();
                existInput.setOrgName(orgName);
                existInput.setValidFlag(new ValidFlag("1"));
                existInput.setCrawlOrgFlag(new CrawlOrgFlag("0"));
            }
            existInput.getDetails().add(detail);
            logger.debug("mergeInputTMDetail: after handle, input.orgName=" + existInput.getOrgName() + ",input.tmAddress=" + existInput.getTmAddress() + ",input.validFlag=" + existInput.getValidFlag().getId() + ",input.crawOrgFlag=" + existInput.getCrawlOrgFlag().getId());

            inputMap.put(orgName, existInput);
        } catch (Throwable e) {
            logger.error("mergeInputTMDetail error", e);
        }
    }

    private static boolean compareTMApplyDate(Trademark tm1, Trademark tm2) {
        try {
            return tm1.getApplyDate().after(tm2.getApplyDate());
        } catch (Throwable e) {
        }
        return false;
    }
}
