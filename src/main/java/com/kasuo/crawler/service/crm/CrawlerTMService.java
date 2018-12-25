//package com.kasuo.crawler.service.crm;
//
//import com.kasuo.crawler.domain.crm.SourceCrawlApplyLog;
//import com.kasuo.crawler.domain.crm.SourceInputStat;
//import com.kasuo.crawler.domain.datadictionary.*;
//import com.kasuo.crawler.domain.source.SourceInput;
//import com.kasuo.crawler.domain.source.SourceInputLog;
//import com.kasuo.crawler.domain.source.SourceInputTMDetail;
//import com.kasuo.crawler.domain.source.SourceUtil;
//import com.kasuo.crawler.domain.trademark.entity.Trademark;
//import com.kasuo.crawler.service.BaituService;
//import com.kasuo.crawler.service.TestService;
//import com.kasuo.crawler.util.DateTool;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * @author dzt
// */
//@Service
//public class CrawlerTMService {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
//    private Thread verifyDataCrawler = null;
//
//    @Autowired
//    private BaituService baituService;
//
//    /**
//     * 从上次抓取的时间
//     * @param lastApplyEndDate 上次抓取的时间末
//     * @param days             抓取天数
//     * @return
//     */
//    public boolean fetchBTVerifyDataAsyn(String lastApplyEndDate, String days) {
//        logger.debug("fetchBTVerifyDataAsyn: lastApplyEndDate=" + lastApplyEndDate + ",days=" + days);
//        if ((verifyDataCrawler != null) && (verifyDataCrawler.isAlive())) {
//            logger.warn("fetchBTVerifyDataAsyn: verifyDataCrawler is running,CANNOT do another task!");
//            throw new RuntimeException("之前的抓取任务正在执行，请等待结束后再启动新的任务");
//        }
//        final Calendar startDate = Calendar.getInstance();
//        startDate.setTime(DateTool.getDate(lastApplyEndDate));
//        startDate.add(Calendar.DAY_OF_YEAR, 1);
//        final Calendar endDate = Calendar.getInstance();
//        endDate.setTime(startDate.getTime());
//        endDate.add(Calendar.DAY_OF_YEAR, Integer.parseInt(days) - 1);
//
////        SystemParamCache.getInstance().update("crawler.bt.applydate.end", DateTool.getStringDate(endDate.getTime()), null);
////        SystemParamCache.getInstance().update("crawler.bt.applydate.days", days, null);
//
//        this.verifyDataCrawler = new Thread(() -> {
//
//            //记录开始爬取状态
//            SourceCrawlApplyLog crawlLog = saveSourceCrawlApplyLog(startDate.getTime(), startDate.getTime());
//            Date delayDate = fetchBaituVerifyData(startDate.getTime(), endDate.getTime(), crawlLog);
//            while (DateTool.dateBeforeEquals(delayDate, endDate.getTime())) {
//                crawlLog = saveSourceCrawlApplyLog(delayDate, delayDate);
//                delayDate = fetchBaituVerifyData(delayDate, endDate.getTime(), crawlLog);
//            }
//        });
//        this.verifyDataCrawler.start();
//        return true;
//    }
//
//    public Date fetchBaituVerifyData(Date startDate, Date endDate, SourceCrawlApplyLog crawlLog)
//    {
//        long beginTime = System.currentTimeMillis();
//        long endTime = 0L;
//
//        Calendar delayDate = Calendar.getInstance();
//        delayDate.setTime(startDate);
//        delayDate.add(Calendar.DAY_OF_YEAR, 1);
//
//        logger.debug("fetchBaituVerifyData: startDate=" + DateTool.getStringDate(startDate) + ",endDate=" + DateTool.getStringDate(endDate) + ",start...");
//        try
//        {
//            if (DateTool.dateAfter(startDate, endDate)) {
//                return startDate;
//            }
//            Calendar applyDate = Calendar.getInstance();
//            applyDate.setTime(startDate);
//
//            List<Trademark> tmList = baituService.queryVerifyDataByApplyDate(applyDate.getTime(), applyDate.getTime());
//            while (tmList.size() < 2000)
//            {
//                applyDate.add(6, 1);
//                delayDate.add(6, 1);
//                if (DateTool.dateAfter(applyDate.getTime(), endDate))
//                {
//                    crawlLog.setMaxApplyDate(endDate);
//                    crawlLog.setStatus(new SourceCrawlBTStatus("2"));
////                    dao().update(crawlLog);
//                    return delayDate.getTime();
//                }
//                tmList = baituService.queryVerifyDataByApplyDate(applyDate.getTime(), applyDate.getTime());
//            }
//            SourceInputLog inputLog = null;
//            String sql = "from SourceInputLog log where log.fileName=?";
////            SourceInputLog oldInputLog = (SourceInputLog)dao().queryFirst(sql, new Object[] { DateTool.getStringDate(applyDate.getTime()) });
////            if (oldInputLog == null)
////            {
////                String batchNo = inputLogService.getNextBatchNo("TM", "_D");
////                logger.debug("fetchBaituVerifyData: createInputLog,batchNo=" + batchNo);
////                inputLog = inputLogService.createInputLog(batchNo, DateTool.getStringDate(applyDate.getTime()), new Priority("2"), new SourceBatchNoticeFlag("0"));
////                if (inputLog == null)
////                {
////                    batchNo = inputLogService.getNextBatchNo("TM", "_D");
////                    inputLog = inputLogService.createInputLog(batchNo, DateTool.getStringDate(applyDate.getTime()), new Priority("2"), new SourceBatchNoticeFlag("0"));
////                }
////            }
////            else
////            {
////                inputLog = oldInputLog;
////            }
//            Map<String, SourceInput> inputMap = new HashMap();
//
//            inputLog.setStatus(new SourceBatchStatus("5"));
//            inputLog.setTotalNums(tmList.size());
////            this.inputLogService.updateInputLogStatus(inputLog, "status;totalNums");
//
//            int validNums = 0;
//
//            Set<String> totalOrgNames = new HashSet();
//            Set<String> validOrgNames = new HashSet();
//            for (Trademark tm : tmList)
//            {
//                totalOrgNames.add(tm.getOrgName());
//                if (tm.getRegNum() != null) {
//                    if ((tm.getAgentName() == null) || (!tm.getAgentName().trim().equals("北京亿拓国际知识产权代理有限公司"))) {
//                        if (SourceUtil.checkValidSourceOrgName(tm.getOrgName(), tm.getTmAddress()))
//                        {
//                            validOrgNames.add(tm.getOrgName());
//                            validNums++;
//
//                            tm.setSourceType(new TMSourceType("3"));
//                            SourceInputTMDetail detail = new SourceInputTMDetail();
//                            detail.setTm(tm);
//                            detail.setBusiType(new TMBusiType("20"));
//                            detail.setErrFlag(new TMSourceErrFlag("0"));
//                            detail.setBusiDate(null);
//                            detail.setOrgName(tm.getOrgName());
//                            detail.setAgentName(tm.getAgentName());
//                            detail.setTmAddress(tm.getTmAddress());
//                            detail.setReadFlag(new ReadFlag("0"));
//                            detail.setReviewFlag(new ReviewFlag("0"));
//
//                            TMFetchFromSMonitor.mergeInputTMDetail(inputMap, detail);
//                        }
//                    }
//                }
//            }
//            endTime = System.currentTimeMillis();
//            long times = (endTime - beginTime) / 1000L;
//
//            SourceInputStat stat = inputService.saveSourceInputFromBaitu(inputLog.getId(), inputMap, times);
//
//            crawlLog.setTotalNums(tmList.size());
//            crawlLog.setValidNums(validNums);
//            crawlLog.setTotalOrgNums(totalOrgNames.size());
//            crawlLog.setValidOrgNums(validOrgNames.size());
//            crawlLog.setNewOrgNums(stat.getNewOrgNums());
//            crawlLog.setNewTmNums(stat.getNewTmNums());
//            crawlLog.setMaxApplyDate(applyDate.getTime());
//            crawlLog.setStatus(new SourceCrawlBTStatus("2"));
//            dao().update(crawlLog);
//
//            endTime = System.currentTimeMillis();
//
//            return delayDate.getTime();
//        }
//        catch (Throwable e)
//        {
//            logger.error("fetchBaituVerifyData error!", e);
//        }
//        return delayDate.getTime();
//    }
//
//    public SourceCrawlApplyLog saveSourceCrawlApplyLog(java.util.Date startDate, java.util.Date endDate)
//    {
//        String sql = "from SourceCrawlApplyLog log where DateDiff(log.minApplyDate,?)=0 ";
//        SourceCrawlApplyLog oldLog = (SourceCrawlApplyLog)dao().queryFirst(sql, new Object[] { startDate });
//        if (oldLog != null)
//        {
//            oldLog.setStatus(new SourceCrawlBTStatus("1"));
//            dao().update(oldLog);
//        }
//        else
//        {
//            oldLog = new SourceCrawlApplyLog();
//            oldLog.setStartType(new SourceCrawlBTStartType("1"));
//            oldLog.setMinApplyDate(startDate);
//            oldLog.setMaxApplyDate(endDate);
//            oldLog.setStatus(new SourceCrawlBTStatus("1"));
//            save(oldLog);
//        }
//        return oldLog;
//    }
//}
