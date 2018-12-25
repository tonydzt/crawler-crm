package com.kasuo.crawler.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kasuo.crawler.dao.TrademarkDao;
import com.kasuo.crawler.dao.mybatis.OrgDao;
import com.kasuo.crawler.domain.SourceOrg;
import com.kasuo.crawler.domain.Trademark;
import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.datadictionary.CrawlFlag;
import com.kasuo.crawler.domain.datadictionary.CurrencyType;
import com.kasuo.crawler.domain.datadictionary.OrgType;
import com.kasuo.crawler.domain.datadictionary.ValidFlag;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.org.OrgHisName;
import com.kasuo.crawler.domain.source.SourceInput;
import com.kasuo.crawler.domain.source.SourceUtil;
import com.kasuo.crawler.exception.DOMParseException;
import com.kasuo.crawler.exception.NotFoundException;
import com.kasuo.crawler.exception.VerifyDataException;
import com.kasuo.crawler.service.crawler.OrgService;
import com.kasuo.crawler.util.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserKeyEvent;
import com.teamdev.jxbrowser.chromium.TabFactory;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JMenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author dzt
 */
@Component
public abstract class AbstractCrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCrawlerService.class);

    public static final int SLEEP_SHORT = 200;
    public static final int SLEEP_MID = 500;
    public static final int SLEEP_LONG = 1000;
    public static final long RETRY_REQUEST_SERVER_INTERVAL = 60000L;
    public static final int MAX_CONTINUOUS_SAVE_FAIL_NUMS = 10;
    public static final int MAX_CONTINUOUS_FAIL_NUMS = 50;
    public static final int MAX_CONTINUOUS_DOM_ERROR_NUMS = 20;
    public static final int MAX_TIMEOUT_NUMS = 10;
    public static final int TIMEOUT_INCREMENT_IN_SECOND = 5;
    public static final int MAX_BASE_TIMEOUT_IN_SECOND = 60;
    public static final int MAX_DETAIL_TIMEOUT_IN_SECOND = 90;
    public static final int VERIFY_CODE_CHECK_THREAD_INTERVAL = 60000;
    public static final int VERIFY_CODE_CHECK_THREAD_TIMEOUT = 600000;
    private static final String SIGNEDCODE = "df3gsfd6x29zm2g";
    private static String serverSign = "U3xer0Xdz9Z2Ea";
    private static int crawlStartMinute = 40;
    private static int crawlEndMinute = 12;
    private static String crawlerStartVerify = "1";

    public static JMenuItem menuItem = null;
    public static JMenuItem menuItem2 = null;

    protected String crawlerType = "2";
    protected String crawlerName = "天眼查";
    protected String crawlerUser = "";
    protected String crawlerPwd = "";
    protected int crawlerIniWait = 2;
    protected int crawlerIncWait = 3;
    protected int crawlerBasicTimeoutCount = 0;
    protected int crawlerDetailTimeoutCount = 0;
    protected int crawlerBasicTimeout = 30;
    protected int crawlerDetailTimeout = 30;
    protected int crawlerMaxRecords = 100;
    protected int crawlerStartHour = 7;
    protected int crawlerEndHour = 23;
    protected String serverIp = null;

    protected String loginUrl = "";
    protected String homeUrl = "";

    protected boolean fetchDataStatus = false;
    protected boolean fetchDataTestStatus = false;

    protected boolean loginPage = false;
    protected String todo = "";

    protected String declineDate = null;
    protected String crawlDate = null;
    protected boolean isOrgInfoBasic = true;
    protected boolean finishLoadingFrame = true;
    protected String queryUrl = "";

    //缓存对象
    protected Map<Long, SourceOrg> handleResultMap = new HashMap();

    protected long crawlStartTime = 0L;
    protected int crawlTotalNums = 0;
    protected int crawlCurrentNums = 0;
    protected int crawlSuccessNums = 0;
    protected int crawlNoFoundNums = 0;
    protected int crawlErrorNums = 0;
    protected int crawlServerErrorNums = 0;
    protected int crawlNetErrorNums = 0;
    protected int crawlDomErrorNums = 0;
    protected int crawlTimeoutErrorNums = 0;
    protected int crawlRebootNums = 0;
    protected int crawlPhoneNums = 0;
    protected int crawlTelNums = 0;
    protected int crawlNoneNums = 0;
    protected int loginNums = 0;
    protected int verifyCodeNums = 0;
    protected int verifyCodePreNums = 0;
    protected long verifyCodePrevTime = 0L;
    protected int verifyCodePrevOrgNums = 0;
    protected int verifyCodePrevReqNums = 0;
    protected Date verifyDataDate = null;
    protected String verifyDataResult = null;

    protected int continuousSaveFailCount = 0;
    protected int continuousTimeoutCount = 0;
    protected int continuousFailCount = 0;
    protected int continuousDomParseErrorCount = 0;
    protected int randWaitTimes = 0;
    protected int remainWaitTimes = 0;

    protected long loadBeginTime = 0L;
    protected long loadEndTime = 0L;
    protected long loadBasicBeginTime = 0L;
    protected long loadBasicEndTime = 0L;
    protected long loadDetailBeginTime = 0L;
    protected long loadDetailEndTime = 0L;

    protected Date lastDistNoFoundOrgDataDate = null;

    protected static AbstractCrawlerService _instance;

    protected CheckVerifyCodeThread checkVerifyCodeThread = null;

    protected AbstractCrawlerService() {
        crawlerType = CrmProperties.getProperty("crawlerType");
        crawlerUser = CrmProperties.getProperty("crawlerUser");
        crawlerPwd = CrmProperties.getProperty("crawlerPwd");
        crawlerIniWait = Integer.parseInt(CrmProperties.getProperty("crawlerIniWait"));
        crawlerIncWait = Integer.parseInt(CrmProperties.getProperty("crawlerIncWait"));
        crawlerBasicTimeout = Integer.parseInt(CrmProperties.getProperty("crawlerBasicTimeout"));
        crawlerDetailTimeout = Integer.parseInt(CrmProperties.getProperty("crawlerDetailTimeout"));
        crawlerMaxRecords = Integer.parseInt(CrmProperties.getProperty("crawlerMaxRecords"));
        crawlerStartHour = Integer.parseInt(CrmProperties.getProperty("crawlerStartHour"));
        crawlerEndHour = Integer.parseInt(CrmProperties.getProperty("crawlerEndHour"));
        crawlerStartVerify = CrmProperties.getProperty("crawlerStartVerify");
        if (crawlerStartVerify == null) {
            crawlerStartVerify = "1";
        }

        if (crawlerType.equals("1")) {
            crawlerName = "启信宝";
        } else if (crawlerType.equals("2")) {
            crawlerName = "天眼查";
        } else if (crawlerType.equals("3")) {
            crawlerName = "企查查";
        } else if (crawlerType.equals("4")) {
            crawlerName = "企查猫";
        }
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public abstract String getLoginUrl();

    public void setFetchDataStatus() {
        fetchDataStatus = (!fetchDataStatus);
        if (!fetchDataStatus)
            fetchDataTestStatus = fetchDataStatus;
    }

    public void setFetchDataStatus(boolean status) {
        fetchDataStatus = status;
        if (!fetchDataStatus)
            fetchDataTestStatus = fetchDataStatus;
    }

    public boolean getFetchDataStatus() {
        return fetchDataStatus;
    }

    public void setFetchDataTestStatus() {
        fetchDataTestStatus = (!fetchDataTestStatus);
        fetchDataStatus = fetchDataTestStatus;
    }

    public void setFetchDataTestStatus(boolean status) {
        fetchDataTestStatus = status;
        fetchDataStatus = fetchDataTestStatus;
    }

    public boolean getFetchDataTestStatus() {
        return fetchDataTestStatus;
    }

    public int getCrawlRebootNums() {
        return crawlRebootNums;
    }

    public void setCrawlRebootNums(int crawlRebootNums) {
        this.crawlRebootNums = crawlRebootNums;
    }

    public void stopFetch() {
        fetchDataStatus = false;
        fetchDataTestStatus = false;
    }

    public void recordBeginLoadingTimes(Browser browser) {
        if ((fetchDataStatus) || (fetchDataTestStatus)) {
            if (isBasicPage(browser)) {
                loadBasicBeginTime = System.currentTimeMillis();
            } else if (isDetailPage(browser)) {
                loadDetailBeginTime = System.currentTimeMillis();
            } else {
                loadBeginTime = System.currentTimeMillis();
            }
        }
    }

    public void recordEndLoadingTimes(Browser browser) {
        if ((fetchDataStatus) || (fetchDataTestStatus)) {
            if (isBasicPage(browser)) {
                loadBasicEndTime = System.currentTimeMillis();
            } else if (isDetailPage(browser)) {
                loadDetailEndTime = System.currentTimeMillis();
            } else {
                loadEndTime = System.currentTimeMillis();
            }
        }
    }


    public long calcLoadingTimes(Browser browser) {
        if (isBasicPage(browser)) {
            return loadBasicEndTime - loadBasicEndTime;
        }
        if (isDetailPage(browser)) {
            return loadDetailEndTime - loadDetailEndTime;
        }
        return loadEndTime - loadEndTime;
    }


    public void startFetch(final Browser browser) {
        Thread fetchThread = new Thread(() -> {
            todo = "";
            List<SourceInput> orgList = null;
            boolean needReboot = false;


            while (fetchDataStatus) {
                try {
                    orgList = null;


                    if (isDecline()) {
                        Thread.sleep(60000L);

                    } else {
                        checkCrawlDate();


                        if (!isCrawlTime(browser)) {
                            Thread.sleep(180000L);
                        } else {
                            String testRun = CrmProperties.getProperty("testRun");
                            if ((testRun != null) && (testRun.equalsIgnoreCase("true"))) {
                                orgList = distributeLocalTestData();
                            } else {
                                orgList = getVerifyData(browser);
                            }
                            boolean verifyData = false;

                            if ((orgList == null) || (orgList.size() == 0)) {
                                orgList = distributeRawData();

                                if ((orgList != null) && (orgList.size() != 0)) {
                                    crawlTotalNums += orgList.size();
                                    crawlCurrentNums += orgList.size();

                                    if (crawlStartTime == 0L) {
                                        crawlStartTime = System.currentTimeMillis();
                                    }
                                }
                            } else {
                                verifyData = true;
                                logger.debug("verifyData = true,now start crawl verifyData...");
                            }

                            if (((orgList == null) || (orgList.size() == 0)) &&
                                    (isKeepSessionTime())) {
                                orgList = distributeNoFoundData();
                            }


                            if ((orgList == null) || (orgList.size() == 0)) {
                                Thread.sleep(60000L);

                            } else {
                                int entryCount = browser.getNavigationEntryCount();

                                for (int i = entryCount - 2; i >= 0; i--) {
                                    browser.removeNavigationEntryAtIndex(i);
                                }


                                browser.getCacheStorage().clearCache();

                                crawlOrgInfo(browser, orgList);

                                if ((!verifyData) && (crawlCurrentNums > crawlerMaxRecords)) {
                                    logger.warn("HAS FETCH " + crawlCurrentNums + " data,now need to REBOOT!");
                                    needReboot = true;
                                    fetchDataStatus = false;

                                    if (checkVerifyCodeThread != null) {
                                        checkVerifyCodeThread.setRunning(false);
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    if ((e instanceof VerifyDataException)) {
                        logger.warn("fetchThread: while loop stop because verifyData fail! ");
                        sendNotice("8");


                        resetFetchStatus(browser, "校验数据停止");
                        break;
                    }
                    logger.error("fetchThread: while loop error", e);
                    sendNotice("8");

                    sendMail(2, "故障停止", "需要检查日志，修改程序！");
                    resetFetchStatus(browser, "故障停止");

                    break;
                }
            }

            showDebugInfo();
            logger.info("fetchThread stopFetch... ");

            if (needReboot) {
                try {
                    logger.warn("needReboot! dispose browser!");
                    crawlCurrentNums = 0;
                    browser.dispose();
                } catch (Throwable e) {
                    logger.error("browser.dispose error!", e);
                }
            }
            logger.info("fetchThread stopFetch over");
        });
        fetchThread.start();


        checkVerifyCodeThread = new CheckVerifyCodeThread();
        new Thread(checkVerifyCodeThread).start();
    }


    class CheckVerifyCodeThread
            implements Runnable {
        private boolean running = true;

        CheckVerifyCodeThread() {
        }

        @Override
        public void run() {
            while ((fetchDataStatus) && (running)) {
                try {
                    if ((verifyCodePreNums != verifyCodeNums) && (verifyCodePrevTime != 0L) &&
                            (System.currentTimeMillis() - verifyCodePrevTime > 600000L)) {
                        sendNotice("1", String.valueOf(verifyCodeNums));
                    }

                    Thread.sleep(60000L);
                } catch (Throwable e) {
                    logger.error("checkVerifyCodeThread: while loop error", e);
                    break;
                }
            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }


    protected List<SourceInput> getVerifyData(Browser browser)
            throws Exception {
        logger.debug("getVerifyData ...");
        List<SourceInput> orgList = null;
        if (verifyDataDate == null) {

//            verifyDataResult = refreshVerifyDataResult();
            verifyDataResult = "0";
            verifyDataDate = new Date();
            if (verifyDataResult.equals("0")) {
                logger.debug("start crawl verifyData...");
                orgList = distributeLocalVerifyData();
            } else if (verifyDataResult.equals("3")) {
                logger.debug("verifyData no pass,stop crawler");
                throw new VerifyDataException("校验数据停止");
            }
        } else if (verifyDataResult.equals("0")) {
            logger.debug("cotinue crawl verifyData...");
            orgList = distributeLocalVerifyData();
        } else if (verifyDataResult.equals("3")) {
            throw new VerifyDataException("校验数据停止");
        }

        return orgList;
    }

    private void crawlOrgInfo(Browser browser, List<SourceInput> inputList) {
        String queryOrgName = null;
        try {
            System.gc();
            Thread.sleep(1000L);


            boolean isTimeout = false;
            String orgName = null;
            String inputId = null;
            long queryTime = 0L;

            for (SourceInput input : inputList) {
                if (!fetchDataStatus) {
                    return;
                }

                com.kasuo.crawler.domain.Org orgTest = SpringBeanUtil.getBean(OrgDao.class).findByNameForUpdate(input.getOrgName());
                if (orgTest != null) {
                    logger.warn("Org {} already exist!", input.getOrgName());
                    SpringBeanUtil.getBean(TrademarkDao.class).updateCrawStatusSkip(Long.parseLong(input.getId()), orgTest.getId(), false);
                    continue;
                }

                if (isDecline()) {
                    resetRawData();
                    return;
                }

                input.setOrgName(SourceUtil.trimOrgName(input.getOrgName()));
                orgName = input.getOrgName();
                inputId = input.getId();
                queryTime = System.currentTimeMillis();

                logger.debug("search OrgInfo for : " + orgName + ",queryTime=" + queryTime);

                loginPage = false;
                isOrgInfoBasic = true;
                finishLoadingFrame = false;

                Org org = new Org();
                org.setCrawlFlag(new CrawlFlag("G"));
                org.setOrgName(orgName);
                queryOrgName = orgName;

                SourceOrg sourceOrg = new SourceOrg();
                sourceOrg.setOrg(org);
                sourceOrg.setQueryOrgName(queryOrgName);
                sourceOrg.setInputId(inputId);
                sourceOrg.setId(queryTime);

                handleResultMap.put(Long.valueOf(queryTime), sourceOrg);

                verifyCodePrevOrgNums += 1;
                verifyCodePrevReqNums += 1;
                verifyCodePreNums = verifyCodeNums;


                loadBasicBeginTime = 0L;
                loadBasicEndTime = 0L;
                loadDetailBeginTime = 0L;
                loadDetailEndTime = 0L;

                queryUrl = getBasicUrl(queryOrgName, queryTime);

                browser.getLocalWebStorage().clear();
                browser.getSessionWebStorage().clear();
                browser.loadURL(queryUrl);

                Thread.sleep(500L);

                long loadUrlBeginTime = System.currentTimeMillis();
                long loadUrlEndTime = 0L;

                for (; ; ) {
                    if (!fetchDataStatus) {
                        return;
                    }

                    if (isDecline()) {
                        resetRawData();
                        return;
                    }

                    loadUrlEndTime = System.currentTimeMillis();

                    if ((loadUrlEndTime - loadUrlBeginTime > (getBasicPageLoadedTimes() + crawlerDetailTimeout) * 1000L) && (!fetchDataTestStatus) &&
                            (verifyCodePreNums == verifyCodeNums)) {
                        if (((loginPage ? 0 : 1) & (declineDate == null ? 1 : 0)) != 0) {
                            logger.error("等待查询结果超时" + (loadUrlEndTime - loadUrlBeginTime) / 1000L + " seconds，跳过本记录：" + queryOrgName + "\r\n");
                            queryOrgName = "NODATA";
                            browser.stop();
                            isTimeout = true;
                            crawlTimeoutErrorNums += 1;
                            sourceOrg.setCrawlEvent("6");
                            org.setCrawlFlag(new CrawlFlag("E"));
                            continuousTimeoutCount += 1;
                            if (continuousTimeoutCount > 50) {
                                logger.warn("连续" + continuousTimeoutCount + "次出现超时! 重置continuousTimeoutCount为0");
                                sendMail(2, "连续" + continuousTimeoutCount + "次出现超时", "连续" + continuousTimeoutCount + "次出现超时，重置continuousTimeoutCount为0<br><br>请检查是否有问题!");
                                continuousTimeoutCount = 0;
                            }

                            saveOrgInfo(browser, org, inputId, sourceOrg.getCrawlEvent());
                            synchronized (this) {
                                handleResultMap.remove(Long.valueOf(queryTime));
                            }
                        }
                    }

                    synchronized (this) {
                        if (!org.getCrawlFlag().isSearching()) {
                            verifyCodePreNums = verifyCodeNums;

                            if (org.getCrawlFlag().isSuccess()) {
                                continuousFailCount = 0;
                                continuousTimeoutCount = 0;
                            } else {
                                continuousFailCount += 1;
                            }

                            if (continuousFailCount > 50) {
                                logger.warn("连续" + continuousFailCount + "次检索失败! 重置continuousFailCount为0");
                                sendMail(2, "连续" + continuousFailCount + "次检索失败", "连续" + continuousFailCount + "次检索失败，重置continuousFailCount为0<br><br>请检查是否有问题!");
                                continuousFailCount = 0;
                            }


                            logger.debug("org.crawlFlag=" + org.getCrawlFlag().getName() + ",storage orgInfo");

                            if (!inputId.equals("VERIFY")) {
                                if (!queryOrgName.equals(org.getOrgName())) {
                                    boolean hisNameFound = false;
                                    for (OrgHisName hisName : org.getOldNames()) {
                                        if (hisName.getOrgName().equals(queryOrgName)) {
                                            hisNameFound = true;
                                        }
                                    }
                                    if (!hisNameFound) {
                                        logger.warn("历史名称不匹配！认为没有找到该机构信息！" + queryOrgName);
                                        org = new Org();
                                        org.setOrgName(queryOrgName);
                                        org.setCrawlFlag(new CrawlFlag("0"));
                                    }
                                }
                                SpringBeanUtil.getBean(OrgService.class).save(org, Long.parseLong(inputId));
//                                saveOrgInfo(browser, org, inputId, sourceOrg.getCrawlEvent());
                            } else {
                                input.setValidFlag(new ValidFlag("1"));
                                if (!checkVerifyData(org, queryOrgName)) {
                                    input.setValidFlag(new ValidFlag("0"));
                                }
                                SourceInput last = (SourceInput) inputList.get(inputList.size() - 1);
                                if ((last.getOrgName().equals(queryOrgName)) &&
                                        (!checkAllVerifyData(inputList))) {
                                    resetFetchStatus(browser, "校验数据停止");

                                    setVerifyDataResult("3");

                                    sendMail(2, "校验数据停止，可能改版", "需要检查日志，修改程序！");
                                }
                            }


                            handleResultMap.remove(Long.valueOf(queryTime));
                            break;
                        }
                    }
                    Thread.sleep(1000L);
                }

                if (!isTimeout) {
                    int incStep = 0;
                    randWaitTimes = CodecTool.random(crawlerIniWait, crawlerIncWait);
                    randWaitTimes += incStep * crawlServerErrorNums;
                    logger.debug("search next record, sleep " + randWaitTimes + " seconds *****************************\r\n");
                    sleep(randWaitTimes * 1000);
                }
            }
        } catch (Throwable e) {
            logger.error("查询企业信息出错: " + queryOrgName, e);
        }
    }

    private void saveOrgInfo(Browser browser, Org org, String inputId, String crawlEvent) {
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "saveFetchedInfo");
        Gson gson = new Gson();
        String json = gson.toJson(org);
        valuePairs.add(new BasicNameValuePair("json", json));
        valuePairs.add(new BasicNameValuePair("inputId", inputId));
        if (org.getCrawlFlag().isError()) {
            valuePairs.add(new BasicNameValuePair("event", crawlEvent));
        }
        String content = httpPostWaiting(valuePairs);
        if (content.trim().equals("")) {
            logger.error("saveOrgInfo server NOT return orgId,orgName=" + org.getOrgName());
            continuousSaveFailCount += 1;
            if (continuousSaveFailCount > 10) {
                continuousSaveFailCount = 0;
                logger.error("saveOrgInfo server NOT return orgId,orgName=" + org.getOrgName() + ",stop crawl...");
                resetFetchStatus(browser, "保存数据故障停止");
            }
        } else {
            continuousSaveFailCount = 0;
        }

        crawlCount(org);
    }

    private void crawlCount(Org org) {
        int phone = 0;
        int tel = 0;
        if (org.getCrawlFlag().isSuccess()) {
            crawlSuccessNums += 1;
            for (Contact contact : org.getContacts()) {
                if ((contact.getTel() != null) && (!contact.getTel().equals(""))) {
                    if (SourceUtil.isPhoneNum(contact.getTel())) {
                        phone++;
                    } else {
                        tel++;
                    }
                }
            }

            if (phone > 0) {
                crawlPhoneNums += 1;
            } else if (tel > 0) {
                crawlTelNums += 1;
            } else {
                crawlNoneNums += 1;
            }
        } else if (org.getCrawlFlag().isNoFound()) {
            crawlNoFoundNums += 1;
        }
    }


    protected boolean checkVerifyDataTel(String tel, int areacodeLength, int telLength) {
        boolean allDigital = true;
        if (tel != null) {
            tel = tel.trim();
            for (int i = 0; i < tel.length(); i++) {
                char c = tel.charAt(i);
                if ((c < '0') || (c > '9')) {
                    allDigital = false;
                    break;
                }
            }
            if (!allDigital) {
                return false;
            }
            if ((tel.length() == 11) || (tel.length() == 11 + areacodeLength) || (tel.length() == telLength) || (tel.length() == areacodeLength + telLength)) {
                return true;
            }
            return false;
        }

        return false;
    }


    protected boolean checkVerifyDataTels(List<Contact> contacts, int areacodeLength, int telLength) {
        for (Contact contact : contacts) {
            if (checkVerifyDataTel(contact.getTel(), areacodeLength, telLength)) {
                return true;
            }
        }
        return false;
    }


    protected boolean checkVerifyDataMails(List<Contact> contacts) {
        for (Contact contact : contacts) {
            if ((contact.getEmail() != null) && (contact.getEmail().contains("@"))) {
                return true;
            }
        }
        return false;
    }


    protected boolean checkVerifyData(Org org, String queryName) {
        try {
            logger.debug("checkVerifyData start...");

            boolean found = false;
            String newValue = "";
            if (queryName.equals("北京中晶环境科技股份有限公司")) {


                found = false;
                newValue = "";
                for (OrgHisName hisName : org.getOldNames()) {
                    newValue = newValue + hisName.getOrgName() + ", ";
                    if (hisName.getOrgName().equals("北京中晶环境科技股份有限公司")) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    logger.error("北京中晶环境科技股份有限公司: 没有找到历史名称！");
                    return false;
                }


                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getTel() + ", ";
                    if ((contact.getTel() != null) && (contact.getTel().contains("87237000"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataTels(org.getContacts(), 3, 8);
                if (!found) {
                    logger.error("北京中晶环境科技股份有限公司: 电话号码不匹配！old=87237000,new=" + newValue);
                    return false;


                }


            } else if (queryName.equals("上海世纪互联信息系统有限公司")) {
                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getTel() + ", ";
                    if ((contact.getTel() != null) && (contact.getTel().contains("02163015500"))) {
                        found = true;
                        break;
                    }
                }

                found = checkVerifyDataTels(org.getContacts(), 3, 8);
                if (!found) {
                    logger.error("上海世纪互联信息系统有限公司: 电话号码不匹配！old=02163015500,new=" + newValue);
                    return false;
                }


                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getEmail() + ", ";
                    if ((contact.getEmail() != null) && (contact.getEmail().equalsIgnoreCase("gongh.jiaqi@21viacloud.com"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataMails(org.getContacts());
                if (!found) {
                    logger.error("上海世纪互联信息系统有限公司: 邮箱不匹配！old=gongh.jiaqi@21viacloud.com,new=" + newValue);
                    return false;
                }
            } else if (queryName.equals("索菲亚管业科技(苏州)有限公司")) {
                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getTel() + ", ";
                    if ((contact.getTel() != null) && (contact.getTel().contains("051263820100"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataTels(org.getContacts(), 4, 8);
                if (!found) {
                    logger.error("索菲亚管业科技(苏州)有限公司: 电话号码不匹配！old=051263820100,new=" + newValue);
                    return false;
                }
            } else if (queryName.equals("沙龙(香港)实业有限公司")) {
                if (!org.getCrawlFlag().isNoFound()) {
                    logger.error("沙龙(香港)实业有限公司: 原来无记录，现在居然找到了！");
                    return false;
                }
            } else if (queryName.equals("北京耀胜体育产业股份有限公司")) {
                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getTel() + ", ";
                    if ((contact.getTel() != null) && (contact.getTel().contains("01052663971"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataTels(org.getContacts(), 3, 8);
                if (!found) {
                    logger.error("北京耀胜体育产业股份有限公司: 电话号码不匹配！old=01052663971,new=" + newValue);
                    return false;
                }

                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getEmail() + ", ";
                    if ((contact.getEmail() != null) && (contact.getEmail().equalsIgnoreCase("767302192@qq.com"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataMails(org.getContacts());
                if (!found) {
                    logger.error("北京耀胜体育产业股份有限公司: 邮箱不匹配！old=767302192@qq.com,new=" + newValue);
                    return false;
                }
            } else if (queryName.equals("神州数码信息服务股份有限公司")) {
                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getTel() + ", ";
                    if ((contact.getTel() != null) && (contact.getTel().contains("075584370701"))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataTels(org.getContacts(), 4, 8);
                if (!found) {
                    logger.error("神州数码信息服务股份有限公司: 电话号码不匹配！old=075584370701,new=" + newValue);
                    return false;
                }

                found = false;
                newValue = "";
                for (Contact contact : org.getContacts()) {
                    newValue = newValue + contact.getEmail() + ", ";
                    if ((contact.getEmail() != null) && ((contact.getEmail().equalsIgnoreCase("tianyuanf@dcits.com")) || (contact.getEmail().equalsIgnoreCase("liwxi@dcits.com")))) {
                        found = true;
                        break;
                    }
                }
                found = checkVerifyDataMails(org.getContacts());
                if (!found) {
                    logger.error("神州数码信息服务股份有限公司: 邮箱不匹配！old=tianyuanf@dcits.com || liwxi@dcits.com,new=" + newValue);
                    return false;
                }
            }
        } catch (Throwable e) {
            logger.error("checkVerifyData error! queryName=" + queryName);
            return false;
        }

        return true;
    }


    protected long getBasicPageLoadedTimes() {
        if (loadBasicEndTime != 0L) {
            return (loadBasicEndTime - loadBasicBeginTime) / 1000L;
        }
        return 0L;
    }


    protected abstract String getBasicUrl(String paramString, long paramLong);


    protected void sendMail(int level, String subject, String content) {
        //TODO
//        NoticeTool.sendMail(level, crawlerName + "[" + crawlerUser + "]" + subject, content);
    }


    protected void sendSMS(int level, String smsCode, Map<String, String> map) {
        if (map == null) {
            map = new HashMap();
        }
        //TODO
//        NoticeTool.sendSMS(0, smsCode, map);
    }

    private List<SourceInput> distributeRawData() {

        List<Trademark> trademarkList = SpringBeanUtil.getBean(TrademarkDao.class).findUnCraw();
        return trademarkList.stream().map(trademark -> {
            SourceInput sourceInput = new SourceInput();
            sourceInput.setId(trademark.getId().toString());
            sourceInput.setOrgName(trademark.getApplicant());
            return sourceInput;
        }).collect(Collectors.toList());
    }

    public List<SourceInput> distributeNoFoundData() {
        List<SourceInput> list = null;
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "distributeNoFoundData");
        String content = httpPostWaiting(valuePairs);

        if (content.equals("")) {
            logger.debug("content is '',no data need to crawl");
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SourceInput>>() {}.getType();
            list = gson.fromJson(content, type);
        }
        return list;
    }


    public void resetFetchStatus(Browser browser, String errMsg) {
        logger.warn("resetFetchStatus,errMsg=" + errMsg);


        fetchDataStatus = false;
        fetchDataTestStatus = false;
        isOrgInfoBasic = false;
        verifyDataDate = null;
        if (menuItem != null) {
            menuItem.setText("启动抓取...");
        }
        if (menuItem2 != null) {
            menuItem2.setText("启动抓取DEBUG");
        }
        if ((browser != null) && (!errMsg.equals(""))) {
            todo = errMsg;
        }
    }


    public void rebootFetchStatus(Browser browser, String errMsg) {
        logger.warn("rebootFetchStatus,errMsg=" + errMsg);


        fetchDataStatus = false;
        fetchDataTestStatus = false;
        isOrgInfoBasic = false;
        if (menuItem != null) {
            menuItem.setText("重启中...");
            menuItem.setEnabled(false);
        }
        if (menuItem2 != null) {
            menuItem2.setText("重启中...");
            menuItem2.setEnabled(false);
        }
    }


    public void rebootOverFetchStatus(Browser browser) {
        if (menuItem != null) {
            menuItem.setText("停止抓取...");
            menuItem.setEnabled(true);
        }
        if (menuItem2 != null) {
            menuItem2.setText("停止抓取DEBUG");
            menuItem2.setEnabled(true);
        }
    }


    private void resetRawData() {
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "resetRawData");
        httpPostWaiting(valuePairs);
    }


    private boolean isCrawlTime(Browser browser) {
        Calendar now = Calendar.getInstance();

        Calendar startTime = Calendar.getInstance();
        startTime.set(1, now.get(1));
        startTime.set(2, now.get(2));
        startTime.set(5, now.get(5));
        startTime.set(11, crawlerStartHour);
        startTime.set(12, crawlStartMinute);
        startTime.set(13, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.set(1, now.get(1));
        endTime.set(2, now.get(2));
        endTime.set(5, now.get(5));
        endTime.set(11, crawlerEndHour);
        endTime.set(12, crawlEndMinute);
        endTime.set(13, 0);

        if ((now.after(startTime)) && (now.before(endTime))) {
            logger.debug("isCrawlTime=true");
            logger.debug("isCrawlTime: nowTime=" + DateTool.getStringDateTime(now.getTime()) + ",startTime=" + DateTool.getStringDateTime(startTime.getTime()) + ",endTime=" + DateTool.getStringDateTime(endTime.getTime()));
            return true;
        }

        if (now.after(endTime)) {
            logger.info("isCrawlTime: is last time,system exit!");
            resetFetchStatus(browser, "休息时间到，正在停止");
            showDebugInfoTodo("休息时间到，正在停止");
            browser.dispose(false);

            if (checkVerifyCodeThread != null) {
                checkVerifyCodeThread.setRunning(false);
            }

            sleep(30000L);

            System.exit(0);
        }
        return false;
    }


    private boolean isKeepSessionTime() {
        Calendar now = Calendar.getInstance();

        int randNum = CodecTool.random(5, 40);
        Calendar startTime = Calendar.getInstance();
        startTime.set(1, now.get(1));
        startTime.set(2, now.get(2));
        startTime.set(5, now.get(5));
        startTime.set(11, 8);
        startTime.set(12, randNum);
        startTime.set(13, 0);

        randNum = CodecTool.random(12, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(1, now.get(1));
        endTime.set(2, now.get(2));
        endTime.set(5, now.get(5));
        endTime.set(11, 23);
        endTime.set(12, randNum);
        endTime.set(13, 0);

        logger.debug("isKeepSessionTime: nowTime=" + DateTool.getStringDateTime(now.getTime()) + ",startTime=" + DateTool.getStringDateTime(startTime.getTime()) + ",endTime=" + DateTool.getStringDateTime(endTime.getTime()));

        if ((now.after(startTime)) && (now.before(endTime))) {
            logger.debug("isKeepSessionTime: is valid time,start check lastDistributeNoFoundDataDate");
            if (lastDistNoFoundOrgDataDate == null) {
                lastDistNoFoundOrgDataDate = now.getTime();
                logger.debug("isKeepSessionTime: lastDistributeNoFoundDataDate is null,return true,lastDistributeNoFoundDataDate is set to now");
                return true;
            }
            Calendar last = Calendar.getInstance();
            last.setTime(lastDistNoFoundOrgDataDate);
            if ((now.get(1) == last.get(1)) && (now.get(2) == last.get(2)) && (now.get(5) == last.get(5))) {
                logger.debug("isKeepSessionTime: lastDistributeNoFoundDataDate is same DAY of now,lastDistributeNoFoundDataDate=" + DateTool.getStringDateTime(last.getTime()));
                randNum = CodecTool.random(5, 3);
                last.add(11, randNum);
                logger.debug("isKeepSessionTime: lastDistributeNoFoundDataDate add " + randNum + " hours,is: " + DateTool.getStringDateTime(last.getTime()));
                if ((last.after(startTime)) && (last.before(endTime)) && (now.after(last))) {
                    lastDistNoFoundOrgDataDate = now.getTime();
                    logger.debug("isKeepSessionTime: return true,lastDistributeNoFoundDataDate is set to now");
                    return true;
                }
                logger.debug("isKeepSessionTime: return false,lastDistributeNoFoundDataDate keep old value");
                return false;
            }

            logger.debug("isKeepSessionTime: lastDistributeNoFoundDataDate is NOT same DAY of now,lastDistributeNoFoundDataDate=" + DateTool.getStringDateTime(last.getTime()));
            lastDistNoFoundOrgDataDate = now.getTime();
            logger.debug("isKeepSessionTime: return true,lastDistributeNoFoundDataDate is set to now");
            return true;
        }


        return false;
    }


    private boolean isVerifyDataTime() {
        Calendar now = Calendar.getInstance();
        Calendar startTime = Calendar.getInstance();
        startTime.set(1, now.get(1));
        startTime.set(2, now.get(2));
        startTime.set(5, now.get(5));
        startTime.set(11, 8);
        startTime.set(12, 3);
        startTime.set(13, 0);

        if (now.after(startTime)) {
            logger.debug("isVerifyDataTime: is valid time,start check verifyData");
            return true;
        }
        return false;
    }


    private List<SourceInput> distributeLocalVerifyData() {
        verifyDataDate = new Date();
        List<SourceInput> result = new ArrayList();
        SourceInput input = null;

        input = new SourceInput();
        input.setId("VERIFY");
        input.setOrgName("北京中晶环境科技股份有限公司");
        result.add(input);

        input = new SourceInput();
        input.setId("VERIFY");
        input.setOrgName("上海世纪互联信息系统有限公司");
        result.add(input);

        input = new SourceInput();
        input.setId("VERIFY");
        input.setOrgName("索菲亚管业科技(苏州)有限公司");
        result.add(input);


        input = new SourceInput();
        input.setId("VERIFY");
        input.setOrgName("北京耀胜体育产业股份有限公司");
        result.add(input);

        input = new SourceInput();
        input.setId("VERIFY");
        input.setOrgName("神州数码信息服务股份有限公司");
        result.add(input);

        return result;
    }


    private List<SourceInput> distributeLocalTestData() {
        List<SourceInput> result = new ArrayList();
        SourceInput input = new SourceInput();
        input.setId("402880ee58a19dc80158a202950f0c11");
        input.setOrgName("北京中晶环境科技股份有限公司");
        result.add(input);

        return result;
    }


    private void checkCrawlDate() {
        if (crawlDate != null) {
            if (!DateTool.getStringDate().equals(crawlDate)) {
                logger.debug("checkCrawlDate: now is next day,reset crawlStat");
                crawlDate = DateTool.getStringDate(new Date());
                crawlStartMinute = CodecTool.random(5, 20);
                crawlEndMinute = CodecTool.random(8, 20);
                resetCrawlStat();
            }
        } else {
            crawlDate = DateTool.getStringDate(new Date());
            crawlStartMinute = CodecTool.random(5, 20);
            crawlEndMinute = CodecTool.random(8, 20);
        }
    }


    public void resetCrawlStat() {
        crawlTotalNums = 0;
        crawlSuccessNums = 0;
        crawlNoFoundNums = 0;
        crawlErrorNums = 0;
        crawlServerErrorNums = 0;
        crawlNetErrorNums = 0;
        crawlDomErrorNums = 0;
        crawlTimeoutErrorNums = 0;

        crawlPhoneNums = 0;
        crawlTelNums = 0;
        crawlNoneNums = 0;
        loginNums = 0;
        verifyCodeNums = 0;
        verifyCodePreNums = 0;
        verifyCodePrevTime = 0L;
        verifyCodePrevOrgNums = 0;
        verifyCodePrevReqNums = 0;


        continuousTimeoutCount = 0;
        continuousFailCount = 0;
    }


    protected boolean isDecline() {
        if (declineDate != null) {
            if (!DateTool.getStringDate().equals(declineDate)) {
                logger.debug("isDecline: now is next day,check now is after 0:05-15");
                int randNum = CodecTool.random(5, 10);
                Calendar now = Calendar.getInstance();
                Calendar base = Calendar.getInstance();
                base.set(Calendar.HOUR_OF_DAY, 0);
                base.set(Calendar.MINUTE, randNum);
                base.set(Calendar.SECOND, 0);
                if (now.after(base)) {
                    logger.debug("isDecline: now is next day and now is after 0:05-15,reset FetchDataStatus.declineDate to null");
                    declineDate = null;
                    return false;
                }
                return true;
            }
            return true;
        }

        return false;
    }


    public String getServerAddress() {
        String localServer = CrmProperties.getProperty("localServer");
        if ((localServer != null) && (localServer.equals("true"))) {
            serverIp = CrmProperties.getProperty("server");
        } else if (serverIp == null) {
            serverIp = queryServerIp();
            if (serverIp != null) {
                serverIp = ("http://" + serverIp + ":12221/crm/");
            }
        }


        return serverIp;
    }


    public void resetServerIp() {
        serverIp = null;
    }

    private String queryServerIp() {
        HttpGet httpGet = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String content = null;
        try {
            httpGet = new HttpGet("http://123.56.80.95/servlet/ServerIp?action=query&sign=" + serverSign);
            response = httpclient.execute(httpGet);
            entity = response.getEntity();
            if (entity != null) {
                content = StringTool.trimAll(EntityUtils.toString(entity, "GBK"));

                logger.debug("server IP: " + content);
            } else {
                logger.error("queryServerIp: can't get response entity");
                return null;
            }
            response.close();
            httpclient.close();
        } catch (Throwable e) {
            logger.error("queryServerIp error", e);
            return null;
        }

        return content;
    }


    public List<NameValuePair> createCommonRequest(String serviceName, String methodName) {
        List<NameValuePair> valuePairs = new ArrayList();
        valuePairs.add(new BasicNameValuePair("service", serviceName));
        valuePairs.add(new BasicNameValuePair("method", methodName));
        valuePairs.add(new BasicNameValuePair("crawlerType", crawlerType));
        valuePairs.add(new BasicNameValuePair("crawlerUser", crawlerUser));
        valuePairs.add(new BasicNameValuePair("signedcode", "df3gsfd6x29zm2g"));

        return valuePairs;
    }


    public String httpPost(List<NameValuePair> valuePairs)
            throws IOException {
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String content = null;
        String service = "";
        String method = "";
        for (NameValuePair pair : valuePairs) {
            if (pair.getName().equals("service")) {
                service = pair.getValue();
            }
            if (pair.getName().equals("method")) {
                method = pair.getValue();
            }
        }
        logger.debug("httpPost begin: service=" + service + ",method=" + method + ",crawlerType=" + crawlerType + ",crawlerUser=" + crawlerUser);
        try {
            String url = getServerAddress() + "json.action";
            httpclient = HttpClients.createDefault();
            httpPost = new HttpPost(url);

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "UTF-8");
            httpPost.setEntity(formEntity);
            response = httpclient.execute(httpPost);
            logger.debug("response status: " + response.getStatusLine().getStatusCode());
            content = EntityUtils.toString(response.getEntity(), "GBK");
            logger.debug("response content: " + content);
        } catch (IOException e) {
            logger.warn(" httpclient.execute error,throw IOException,retry get serverIP");
            throw e;
        } catch (Throwable e) {
            logger.error("httpclient request error", e);
            try {
                response.close();
                httpclient.close();
            } catch (Throwable e1) {
                logger.error("close httpclient error", e1);
            }
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (Throwable e) {
                logger.error("close httpclient error", e);
            }
        }

        logger.debug("httpPost end");
        return content;
    }


    public String httpPostWaiting(List<NameValuePair> valuePairs) {
        for (; ; ) {
            try {
                return httpPost(valuePairs);
            } catch (IOException e) {
                logger.warn("httpPostWaiting: can't connect crmServer,try get serverIP again");
                resetServerIp();
                try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } catch (Throwable localThrowable) {
            }
        }
    }


    public String httpPostOnce(List<NameValuePair> valuePairs) {
        try {
            return httpPost(valuePairs);
        } catch (IOException e) {
        }
        return null;
    }


    public boolean checkAllVerifyData(List<SourceInput> inputList) {
        boolean foundPass = false;
        for (SourceInput input : inputList) {
            if (input.getValidFlag().isValid()) {
                foundPass = true;
                break;
            }
        }
        if (foundPass) {
            logger.debug("checkAllVerifyData: passed");
//            setVerifyDataResult("2");
            verifyDataResult = "2";
        } else {
            logger.debug("checkAllVerifyData: NOT passed");
            setVerifyDataResult("3");
            verifyDataResult = "3";
        }

        return foundPass;
    }


    public void setVerifyDataResult(String result) {
        logger.debug("setVerifyDataResult");
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "setVerifyDataResult");
        valuePairs.add(new BasicNameValuePair("result", result));
        httpPostWaiting(valuePairs);
    }


    public String refreshVerifyDataResult() {
        logger.debug("refreshVerifyDataResult start...");
        String result = null;
        try {
            for (; ; ) {
                List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "refreshVerifyDataResult");
                valuePairs.add(new BasicNameValuePair("startFlag", crawlerStartVerify));
                result = httpPostWaiting(valuePairs);
                logger.debug("refreshVerifyDataResult: " + result);
                if (!result.equals("1")) {
                    logger.debug("refreshVerifyDataResult get result: " + result + ",stop loop");
                    break;
                }
                sleep(60000L);
            }
        } catch (Throwable e) {
            logger.error("refreshVerifyDataResult error", e);
        }

        return result;
    }


    public void initBrowser(Browser browser) {
    }


    public String pageType(Browser browser) {
        String s = null;

        if (isBasicPage(browser)) {
            s = "概要页面";
        } else if (isDetailPage(browser)) {
            s = "详情页面";
        } else if (isHomePage(browser)) {
            s = "首页页面";
        } else if (isLoginIdentityPage(browser)) {
            s = "认证页面";
        } else if (isLoginPage(browser)) {
            s = "登录页面";
        } else {
            s = "未知页面  url=" + browser.getURL();
        }
        return s;
    }


    protected abstract boolean isDeclinePage(Browser paramBrowser);


    protected abstract boolean isHomePage(Browser paramBrowser);


    protected abstract boolean isLoginPage(Browser paramBrowser);


    protected abstract boolean isLoginIdentityPage(Browser paramBrowser);

    protected abstract boolean isBasicPage(Browser paramBrowser);

    protected abstract boolean isDetailPage(Browser paramBrowser);

    protected abstract boolean isVerifyCodePage(Browser paramBrowser);

    protected abstract boolean isErrorPage(Browser paramBrowser);

    protected void handleLoginPage(Browser browser) {
        loginPage = true;
        if (fetchDataStatus) {
            loginNums += 1;
            logger.warn("********************************* 出现登录页 " + loginNums + " 次 *********************************");
            showDebugInfoTodo("要求登录");
            sendNotice("2");
        }
    }

    protected void handleDeclinePage(Browser browser) {
        declineDate = DateTool.getStringDate();
        logger.warn("当前账户达到每日查询数据上限，系统暂停抓取数据，等待第二天自动清除上限限制");
        showDebugInfoTodo("数据上限");
        sendNotice("3");
    }


    protected void handleVerifyCodePage(Browser browser) {
        verifyCodeNums += 1;
        String verifyTimes = "0";
        if (verifyCodePrevTime != 0L) {
            verifyTimes = DateTool.secToTime((System.currentTimeMillis() - verifyCodePrevTime) / 1000L);
        }

        String msg = "出现次数: " + verifyCodeNums + "，上次至今: " + verifyTimes + "，检索机构: " + verifyCodePrevOrgNums + "，检索请求: " + verifyCodePrevReqNums;
        logger.warn("********************************* 出现验证码 ***********************************************");
        logger.warn(msg);
        logger.warn("*******************************************************************************************");


        verifyCodePrevOrgNums = 1;
        verifyCodePrevReqNums = 1;
        verifyCodePrevTime = System.currentTimeMillis();
        showDebugInfoTodo("验证码");
        TabFactory.selectTab(browser);
        sendNotice("1", String.valueOf(verifyCodeNums));
    }


    protected SourceOrg getMySourceOrg(Browser browser) {
        String url = browser.getURL();
        String queryTime = null;
        int i = url.indexOf('?');
        if (i != -1) {
            String s = url.substring(i + 1);
            String[] ss = s.split("&");
            for (int k = 0; k < ss.length; k++) {
                String[] sss = ss[k].split("=");
                if (sss[0].equals("et")) {
                    queryTime = sss[1];
                    break;
                }
            }
        }
        if (queryTime != null) {
            synchronized (this) {
                return (SourceOrg) handleResultMap.get(Long.valueOf(Long.parseLong(queryTime)));
            }
        }
        //et应该就是请求时间戳 tony
        logger.error("getMySourceOrg error! NOT FOUND et param! url=" + url);
        return null;
    }


    protected void handleErrorPage(Browser browser) {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg != null) {
            Org org = sourceOrg.getOrg();
            sourceOrg.setCrawlEvent("4");
            org.setCrawlFlag(new CrawlFlag("E"));
        }
    }


    protected abstract boolean handleQueryNums(Browser paramBrowser, boolean paramBoolean)
            throws DOMParseException, NotFoundException;


    protected abstract boolean handleQueryList(Browser paramBrowser, boolean paramBoolean)
            throws DOMParseException, NotFoundException;


    protected abstract String fetchBasicInfo(Browser paramBrowser, Org paramOrg);


    protected abstract void fetchDetailInfo(Browser paramBrowser, Org paramOrg);


    private void showDebugInfoTodo(String msg) {
        try {
            Browser browser = TabFactory.browserHome;
            if (browser == null) {
                return;
            }

            DOMDocument doc = browser.getDocument();
            DOMElement todoDiv = doc.findElement(By.id("tomDebugTodo"));

            if (todoDiv != null) {
                todoDiv.setInnerHTML(msg);
            }
        } catch (Throwable localThrowable) {
        }
    }

    private void clearDebugInfoTodo() {
        try {
            Browser browser = TabFactory.browserHome;
            if (browser == null) {
                return;
            }
            DOMDocument doc = browser.getDocument();
            DOMElement todoDiv = doc.findElement(By.id("tomDebugTodo"));

            if (todoDiv != null) {
                todoDiv.setInnerHTML("");
            }
        } catch (Throwable localThrowable) {
        }
    }

    private void createDebugInfo() {
        try {
            Browser browser = TabFactory.browserHome;
            if (browser == null) {
                return;
            }
            DOMDocument doc = browser.getDocument();
            if (doc != null) {
                DOMNode body = doc.findElement(By.tagName("body"));

                DOMElement statusDiv = doc.createElement("div");
                body.appendChild(statusDiv);
                statusDiv.setAttribute("id", "tomDebugStatus");
                statusDiv.setAttribute("style", "display:block;position:absolute;font-size:30px;font-weight:bold;color:#D71518;z-index:9999999;left:20px;top:90px;");


                DOMElement todoDiv = doc.createElement("div");
                body.appendChild(todoDiv);
                todoDiv.setAttribute("id", "tomDebugTodo");
                todoDiv.setAttribute("style", "display:block;position:absolute;font-size:30px;font-weight:bold;color:#EEA236;z-index:9999999;left:200px;top:90px;");


                DOMElement countDiv = doc.createElement("div");
                body.appendChild(countDiv);
                countDiv.setAttribute("id", "tomDebugCount");
                countDiv.setAttribute("style", "display:block;position:absolute;font-size:20px;font-weight:bold;color:#D71518;z-index:9999999;left:20px;top:235px;");
            }
        } catch (Throwable localThrowable) {
        }
    }

    public void showDebugInfo() {
        try {
            Browser browser = TabFactory.browserHome;
            if (browser == null) {
                return;
            }

            DOMDocument doc = browser.getDocument();

            String status = null;
            String count = null;

            if (fetchDataStatus) {
                status = "正在检索...<br>";
            } else {
                status = "已停止检索<br>";
            }
            status = status + "<span style='font-size:20px;font-weight:400;'>" + crawlerUser + "<br>";
            status = status + crawlerPwd + "</span>";

            DOMElement statusDiv = doc.findElement(By.id("tomDebugStatus"));
            if (statusDiv == null) {
                createDebugInfo();
                statusDiv = doc.findElement(By.id("tomDebugStatus"));
            }
            statusDiv.setInnerHTML(status);

            DOMElement todoDiv = doc.findElement(By.id("tomDebugTodo"));
            if (todoDiv != null) {
                todoDiv.setInnerHTML(todo);
            }
            String verifyTime = "";
            if (verifyCodePrevTime != 0L) {
                verifyTime = DateTool.getStringDateTime(verifyCodePrevTime);
            }
            if (crawlStartTime != 0L) {
                count = "开始时间：" + DateTool.getStringDateTime(crawlStartTime) + "<br>检索耗时：" + DateTool.secToTime((System.currentTimeMillis() - crawlStartTime) / 1000L) + "<br>";
                count = count + "重启次数：" + crawlRebootNums + "<br>验证出现：" + verifyTime + "<br>验证码数：" + verifyCodeNums + "<br>登录页数：" + loginNums + "<br>";
            } else {
                count = "开始时间：<br>检索耗时：<br>重启次数：<br>验证出现：<br>验证码数：" + verifyCodeNums + "<br>登录页数：" + loginNums + "<br>";
            }
            count = count + "服务故障：" + crawlServerErrorNums + "<br>网络故障：" + crawlNetErrorNums + "<br>" + "检索超时：" + crawlTimeoutErrorNums + "<br>";
            count = count + "已检索数：" + (crawlSuccessNums + crawlNoFoundNums + crawlErrorNums) + "<br>检索成功：" + crawlSuccessNums + "<br>检索失败：" + crawlNoFoundNums + "<br>";
            count = count + "手机号码：" + crawlPhoneNums + "<br>固定电话：" + crawlTelNums + "<br>无号码数：" + crawlNoneNums;

            DOMElement countDiv = doc.findElement(By.id("tomDebugCount"));
            countDiv.setInnerHTML(count);
        } catch (Throwable localThrowable) {
        }
    }

    private void showDebugTimes() {
        try {
            Browser browser = TabFactory.browserHome;
            if (browser == null) {
                return;
            }

            DOMDocument doc = browser.getDocument();
            if (doc != null) {
                String times = null;
                times = "等待计时：" + randWaitTimes + "/" + remainWaitTimes;

                DOMElement timeDiv = doc.findElement(By.id("tomDebugTime"));
                if (timeDiv == null) {
                    createDebugInfo();
                    timeDiv = doc.findElement(By.id("tomDebugTime"));
                }
                timeDiv.setInnerHTML(times);
            }
        } catch (Throwable localThrowable) {
        }
    }


    public void handleOrgInfo(Browser browser, FinishLoadingEvent event)
            throws Exception {
        loginPage = false;

        if ((event.isMainFrame()) && (isLoginPage(browser))) {
            handleLoginPage(browser);
            return;
        }

        if ((event.isMainFrame()) && (isHomePage(browser))) {
            return;
        }

        if ((event.isMainFrame()) && (isDeclinePage(browser))) {
            handleDeclinePage(browser);
            return;
        }

        if ((event.isMainFrame()) && (fetchDataStatus)) {
            logger.debug("         *******  onFinishLoadingFrame begin handle: fetchDataStatus=true ...");
            sleep(1000L);


            if (isVerifyCodePage(browser)) {
                handleVerifyCodePage(browser);
                return;
            }
            TabFactory.selectTab("Home");


            if (isErrorPage(browser)) {
                handleErrorPage(browser);
                return;
            }

            clearDebugInfoTodo();

            SourceOrg sourceOrg = getMySourceOrg(browser);
            if (sourceOrg == null) {
                logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
                return;
            }
            Org org = sourceOrg.getOrg();

            if (isBasicPage(browser)) {
                logger.debug("search org basic info: handle document begin...");
                try {
                    boolean handleQueryNumsResult = handleQueryNums(browser, true);
                    if (!handleQueryNumsResult) {


                        sleep(10000L);
                        handleQueryNumsResult = handleQueryNums(browser, false);
                        if (!handleQueryNumsResult) {
                            return;
                        }
                    }
                } catch (DOMParseException e) {
                    sourceOrg.setCrawlEvent("7");
                    org.setCrawlFlag(new CrawlFlag("E"));
                    return;
                } catch (NotFoundException e) {
                    org.setCrawlFlag(new CrawlFlag("0"));
                    return;
                }

                try {
                    boolean handleQueryListResult = handleQueryList(browser, true);
                    if (!handleQueryListResult) {
                        handleQueryListResult = handleQueryList(browser, false);
                        if (!handleQueryListResult) {
                            return;
                        }
                    }
                } catch (DOMParseException e) {
                    sourceOrg.setCrawlEvent("7");
                    org.setCrawlFlag(new CrawlFlag("E"));
                    return;
                } catch (NotFoundException e) {
                    org.setCrawlFlag(new CrawlFlag("0"));
                    return;
                }


                String url;
                try {
                    url = fetchBasicInfo(browser, org);
                } catch (DOMParseException e) {
                    continuousDomParseErrorCount += 1;
                    if (continuousDomParseErrorCount > 20) {
                        resetFetchStatus(browser, "查询故障");
                        sendMail(2, "查询故障！已停止检索", "查询(" + sourceOrg.getQueryOrgName() + ")概要信息出错<br><br>已停止检索,需人工解决!");
                        return;
                    }
                    logger.warn("fetchBasicInfo parseDOM error,give up search org detail info");
                    crawlDomErrorNums += 1;
                    sourceOrg.setCrawlEvent("7");
                    org.setCrawlFlag(new CrawlFlag("E"));
                    return;
                }


                continuousDomParseErrorCount = 0;


                if (url != null) {
                    logger.debug("search org basic info: handle document end, FOUND org basic info, detail info url: " + url);
                    sleep(1000L);

                    verifyCodePrevReqNums += 1;

                    browser.getLocalWebStorage().clear();
                    browser.getSessionWebStorage().clear();
                    browser.loadURL(url + "?et=" + sourceOrg.getId());
                } else {
                    logger.debug("NOT FOUND org basic info,give up search org detail info");
                    org.setCrawlFlag(new CrawlFlag("0"));
                }
            } else {
                logger.debug("search org detail info: handle document begin...");
                try {
                    fetchDetailInfo(event.getBrowser(), org);
                } catch (DOMParseException e) {
                    continuousDomParseErrorCount += 1;
                    if (continuousDomParseErrorCount > 20) {
                        resetFetchStatus(browser, "查询故障");
                        sendMail(2, "查询故障！查询(" + sourceOrg.getQueryOrgName() + ")详情信息出错", "查询(" + sourceOrg.getQueryOrgName() + ")详情信息出错<<br><br>已停止检索,需人工解决!");
                        return;
                    }
                    logger.debug("fetchDetailInfo parse error,give up search org detail info");
                    sourceOrg.setCrawlEvent("7");
                    org.setCrawlFlag(new CrawlFlag("E"));
                    return;
                }

                continuousDomParseErrorCount = 0;
                org.setCrawlFlag(new CrawlFlag("1"));
                logger.debug("search org detail info: handle document end");
            }
        }
    }


    protected static CurrencyType getCurrencyType(String capitalUnit) {
        CurrencyType currencyType = null;
        if (capitalUnit.contains("人民币")) {
            currencyType = new CurrencyType("CNY");
        } else if (capitalUnit.contains("美元")) {
            currencyType = new CurrencyType("USD");
        } else if (capitalUnit.contains("港币")) {
            currencyType = new CurrencyType("HKD");
        } else if (capitalUnit.contains("欧元")) {
            currencyType = new CurrencyType("EUR");
        } else if (capitalUnit.contains("英镑")) {
            currencyType = new CurrencyType("GBP");
        } else if (capitalUnit.contains("日元")) {
            currencyType = new CurrencyType("JPY");
        } else if (capitalUnit.contains("韩元")) {
            currencyType = new CurrencyType("KRW");
        } else if (capitalUnit.contains("加元")) {
            currencyType = new CurrencyType("CAD");
        } else if (capitalUnit.contains("澳元")) {
            currencyType = new CurrencyType("AUD");
        } else if (capitalUnit.contains("瑞郎")) {
            currencyType = new CurrencyType("CHF");
        } else
            currencyType = new CurrencyType("n");
        return currencyType;
    }


    protected static int getRegCapitalInt(String regCapital) {
        try {
            int iDecollator = regCapital.indexOf(".");
            if (iDecollator != -1) {
                regCapital = regCapital.substring(0, iDecollator).trim();
            }
            int iRegCapital = 0;

            return Integer.parseInt(regCapital);
        } catch (Throwable e) {
        }
        return 0;
    }

    protected static String getWebUrl(String web) {
        try {
            if (web == null) {
                return null;
            }
            web = StringTool.trimAll(web).replaceAll("：", ":").replaceAll("\\\\", "/").trim();
            if (web.startsWith("//")) {
                web = web.substring(2);
            }
            if (web.startsWith("/")) {
                web = web.substring(1);
            }
            if (web.contains(".")) {
                return web.toLowerCase();
            }
            return null;
        } catch (Throwable e) {
            logger.error("getWebUrl", e);
        }
        return null;
    }


    protected static OrgType getOrgType(String orgType) {
        if ((orgType.contains("个体工商户")) || (orgType.contains("个体户"))) {
            return new OrgType("2");
        }
        return new OrgType("1");
    }

    protected static String getOrgStatus(String s) {
        try {
            s = s.trim();
            int i = s.indexOf("（");
            if (i != -1) {
            }
            return s.substring(0, i).trim();
        } catch (Throwable e) {
            logger.error("getOrgStatus error! " + s, e);
        }
        return null;
    }


    public void sendNotice(String eventId) {
        logger.info("sendNotice: eventId=" + eventId);
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "receiveNotice");
        valuePairs.add(new BasicNameValuePair("event", eventId));
        httpPostWaiting(valuePairs);
    }

    public void sendNotice(String eventId, String flag) {
        logger.info("sendNotice: eventId=" + eventId);
        List<NameValuePair> valuePairs = createCommonRequest("crawlerOrgService", "receiveNotice");
        valuePairs.add(new BasicNameValuePair("event", eventId));
        valuePairs.add(new BasicNameValuePair("flag", flag));
        httpPostWaiting(valuePairs);
    }


    public static void sleep(long times) {
        try {
            Thread.sleep(times);
        } catch (Throwable e) {
            logger.error("sleep error", e);
        }
    }


    protected static void forwardKeyEvent(Browser browser, String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            browser.forwardKeyEvent(new BrowserKeyEvent(BrowserKeyEvent.KeyEventType.TYPED, getKeyCode(c), c));
        }
    }

    private static BrowserKeyEvent.KeyCode getKeyCode(char c) {
        switch (c) {
            case '0':
                return BrowserKeyEvent.KeyCode.VK_0;
            case '1':
                return BrowserKeyEvent.KeyCode.VK_1;
            case '2':
                return BrowserKeyEvent.KeyCode.VK_2;
            case '3':
                return BrowserKeyEvent.KeyCode.VK_3;
            case '4':
                return BrowserKeyEvent.KeyCode.VK_4;
            case '5':
                return BrowserKeyEvent.KeyCode.VK_5;
            case '6':
                return BrowserKeyEvent.KeyCode.VK_6;
            case '7':
                return BrowserKeyEvent.KeyCode.VK_7;
            case '8':
                return BrowserKeyEvent.KeyCode.VK_8;
            case '9':
                return BrowserKeyEvent.KeyCode.VK_9;
            case 'a':
                return BrowserKeyEvent.KeyCode.VK_A;
            case 'b':
                return BrowserKeyEvent.KeyCode.VK_B;
            case 'c':
                return BrowserKeyEvent.KeyCode.VK_C;
            case 'd':
                return BrowserKeyEvent.KeyCode.VK_D;
            case 'e':
                return BrowserKeyEvent.KeyCode.VK_E;
            case 'f':
                return BrowserKeyEvent.KeyCode.VK_F;
            case 'g':
                return BrowserKeyEvent.KeyCode.VK_G;
            case 'h':
                return BrowserKeyEvent.KeyCode.VK_H;
            case 'i':
                return BrowserKeyEvent.KeyCode.VK_I;
            case 'j':
                return BrowserKeyEvent.KeyCode.VK_J;
            case 'k':
                return BrowserKeyEvent.KeyCode.VK_K;
            case 'l':
                return BrowserKeyEvent.KeyCode.VK_L;
            case 'm':
                return BrowserKeyEvent.KeyCode.VK_M;
            case 'n':
                return BrowserKeyEvent.KeyCode.VK_N;
            case 'o':
                return BrowserKeyEvent.KeyCode.VK_O;
            case 'p':
                return BrowserKeyEvent.KeyCode.VK_P;
            case 'q':
                return BrowserKeyEvent.KeyCode.VK_Q;
            case 'r':
                return BrowserKeyEvent.KeyCode.VK_R;
            case 's':
                return BrowserKeyEvent.KeyCode.VK_S;
            case 't':
                return BrowserKeyEvent.KeyCode.VK_T;
            case 'u':
                return BrowserKeyEvent.KeyCode.VK_U;
            case 'v':
                return BrowserKeyEvent.KeyCode.VK_V;
            case 'w':
                return BrowserKeyEvent.KeyCode.VK_W;
            case 'x':
                return BrowserKeyEvent.KeyCode.VK_X;
            case 'y':
                return BrowserKeyEvent.KeyCode.VK_Y;
            case 'z':
                return BrowserKeyEvent.KeyCode.VK_Z;
        }

        return BrowserKeyEvent.KeyCode.VK_0;
    }


    public static void main(String[] args) {
    }


    public void handleFailLoadingFrame(FailLoadingEvent event) {
        synchronized (this) {
            if (((fetchDataStatus) || (fetchDataTestStatus)) && (
                    (isBasicPage(event.getBrowser())) || (isDetailPage(event.getBrowser())))) {
                SourceOrg sourceOrg = getMySourceOrg(event.getBrowser());
                if (sourceOrg == null) {
                    logger.warn("NOT FOUND sourceOrg,url=" + event.getBrowser().getURL());
                    return;
                }
                Org org = sourceOrg.getOrg();
                String queryOrgName = sourceOrg.getQueryOrgName();

                logger.error("handleFailLoadingFrame，认为页面加载出错，继续下一条，请人工检查日志! " + queryOrgName);

                crawlNetErrorNums += 1;
                sourceOrg.setCrawlEvent("5");
                org.setCrawlFlag(new CrawlFlag("E"));
            }
        }
    }


    protected List<Contact> mergeContact(Contact c1, List<Contact> oldList) {
        List<Contact> addList = new ArrayList();

        if (c1.getTel() != null) {
            boolean foundTel = false;
            boolean foundBlankTel = false;
            for (Contact c2 : oldList) {
                if (c1.getTel().equals(c2.getTel())) {
                    foundTel = true;
                    if (c1.getEmail() == null) {
                        break;
                    }
                    c2.setEmail(c1.getEmail());

                    break;
                }
            }
            if (!foundTel) {
                for (Contact c2 : oldList) {
                    if ((c2.getTel() == null) || (c2.getTel().trim().equals(""))) {
                        foundBlankTel = true;
                        c2.setTel(c1.getTel());
                        if (c1.getEmail() == null) {
                            break;
                        }
                        c2.setEmail(c1.getEmail());

                        break;
                    }
                }
            }
            if ((!foundTel) && (!foundBlankTel)) {
                addList.add(c1);
            }
        } else if (c1.getEmail() != null) {
            boolean foundMail = false;
            boolean foundBlankMail = false;
            for (Contact c2 : oldList) {
                if (c1.getEmail().equals(c2.getEmail())) {
                    foundMail = true;
                    break;
                }
            }

            if (!foundMail) {
                for (Contact c2 : oldList) {
                    if ((c2.getEmail() == null) || (c2.getEmail().trim().equals(""))) {
                        foundBlankMail = true;
                        c2.setEmail(c1.getEmail());
                        break;
                    }
                }
            }
            if ((!foundMail) && (!foundBlankMail)) {
                addList.add(c1);
            }
        }


        oldList.addAll(addList);

        return oldList;
    }
}
