package com.kasuo.crawler.service;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kasuo.crawler.domain.SourceOrg;
import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.datadictionary.CurrencyType;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.org.OrgHisName;
import com.kasuo.crawler.domain.source.SourceUtil;
import com.kasuo.crawler.exception.DOMParseException;
import com.kasuo.crawler.exception.NotFoundException;
import com.kasuo.crawler.util.DateTool;
import com.kasuo.crawler.util.StringTool;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CrawlerOrgTYCService extends AbstractCrawlerService {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerOrgTYCService.class);

    private String homeUrl = "https://www.tianyancha.com";

    protected CrawlerOrgTYCService() {
    }

    @Override
    protected boolean isDeclinePage(Browser browser) {
        String url = browser.getURL();

        if (url.contains("www.tianyancha.com/decline")) {
            return true;
        }
        String html = browser.getHTML();
        return html.contains("已达上限");
    }

    @Override
    protected boolean isHomePage(Browser browser) {
        String url = browser.getURL();
        return "http://www.tianyancha.com/".equals(url) || "http://www.tianyancha.com".equals(url) || "https://www.tianyancha.com/".equals(url) || "https://www.tianyancha.com".equals(url);
    }

    @Override
    protected boolean isLoginPage(Browser browser) {
        String url = browser.getURL();
        return url.startsWith("http://www.tianyancha.com/login") || url.startsWith("https://www.tianyancha.com/login");
    }

    @Override
    protected boolean isLoginIdentityPage(Browser browser) {
        String url = browser.getURL();
        return url.contains("www.tianyancha.com/auth/identity");
    }

    @Override
    protected boolean isBasicPage(Browser browser) {
        String url = browser.getURL();
        return url.startsWith("http://www.tianyancha.com/search?key=") || url.startsWith("https://www.tianyancha.com/search?key=");
    }

    @Override
    protected boolean isDetailPage(Browser browser) {
        String url = browser.getURL();
        return url.startsWith("http://www.tianyancha.com/company/") || url.startsWith("https://www.tianyancha.com/company/");
    }


    @Override
    protected boolean isVerifyCodePage(Browser browser) {
        DOMDocument doc = browser.getDocument();
        String html = doc.getDocumentElement().getInnerHTML();

        DOMElement checkFalse = doc.findElement(By.id("checkFalse"));
        DOMElement myCanvas = doc.findElement(By.id("myCanvas"));
        DOMElement targetImg = doc.findElement(By.id("targetImg"));

        if ((checkFalse != null) || (myCanvas != null) || (targetImg != null) || (html.contains("天眼查校验")) || (html.contains("我们只是确认一下你不是机器人"))) {
            return true;
        }
        return false;
    }


    @Override
    protected boolean isErrorPage(Browser browser) {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return true;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        DOMDocument doc = browser.getDocument();
        String html = doc.getDocumentElement().getInnerHTML();
        DOMElement titleError = doc.findElement(By.tagName("title"));
        if ((titleError != null) && ("Error".equalsIgnoreCase(titleError.getInnerText().trim()))) {
            logger.warn("FOUND error-container,出现错误页面提示，继续检索下一条记录（当前记录会最后再次检索）! " + queryOrgName);
            logger.error("******************************************* ERROR html *******************************************");
            logger.error(browser.getHTML());
            return true;
        }
        DOMElement mainFrameError = doc.findElement(By.id("main-frame-error"));
        if (mainFrameError != null) {
            logger.warn("FOUND main-frame-error,出现错误页面提示，继续检索下一条记录（当前记录会最后再次检索）! " + queryOrgName);
            logger.error("******************************************* ERROR html *******************************************");
            logger.error(browser.getHTML());
            return true;
        }


        if (html.contains("你的页面被黑洞吞了")) {
            logger.warn("FOUND 你的页面被黑洞吞了,出现错误页面提示，继续检索下一条记录（当前记录会最后再次检索）! " + queryOrgName);
            logger.error("******************************************* ERROR html *******************************************");
            logger.error(browser.getHTML());
            return true;
        }

        return false;
    }

    @Override
    protected void handleLoginPage(Browser browser) {
        super.handleLoginPage(browser);

        String js = null;
        sleep(500L);


        List<DOMElement> users = browser.getDocument().findElements(By.className("fa-user"));
        DOMNode e;
        for (DOMElement ele : users) {
            e = ele.getNextSibling();
            if (("ElementNode".equals(e.getNodeType().name())) && ("SPAN".equals(e.getNodeName())) && (e.getTextContent().trim().equals(crawlerUser))) {
                logger.debug("天眼查已登录！不需要再次登录，跳转到查询首页");
                browser.loadURL(homeUrl);
                return;
            }
        }


        js = "$('.mobile_box .contactphone').focus();";
        browser.executeJavaScript(js);

        forwardKeyEvent(browser, crawlerUser);

        sleep(500L);

        js = "$('.mobile_box .contactword').focus();";
        browser.executeJavaScript(js);

        forwardKeyEvent(browser, crawlerPwd);
    }


    /**
     * 检查是否搜索到结果
     */
    @Override
    protected boolean handleQueryNums(Browser browser, boolean retry)
            throws DOMParseException, NotFoundException {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return false;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        DOMDocument doc = browser.getDocument();

        DOMElement container = doc.findElement(By.id("search"));
        if (container == null) {
            if (isNoData(doc)) {
                logger.debug("search result 无结果 , give up search org detail info,throw NotFoundException");
                throw new NotFoundException();
            }
            crawlDomErrorNums += 1;
            if (!retry) {
                logger.error("NOT FOUND #search,天眼查出现未知页面失败，认为解析DOM出错，继续下一条，请人工检查日志! " + queryOrgName);
                throw new DOMParseException();
            }

            logger.warn("NOT FOUND #search,天眼查出现未知页面失败，本页面等待N秒后再次尝试解析'找到N条相关结果' " + queryOrgName);

            logger.error("******************************************* ERROR html *******************************************");
            logger.error(doc.getDocumentElement().getInnerHTML());
            return false;
        }

        return true;
    }

    /**
     * 检查是否搜索到结果
     */
    @Override
    protected boolean handleQueryList(Browser browser, boolean retry)
            throws DOMParseException, NotFoundException {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return false;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        DOMDocument doc = browser.getDocument();

        List<DOMElement> items = doc.findElements(By.className("search-result-single"));

        if ((items == null) || (items.size() == 0)) {
            if (isNoData(doc)) {
                logger.debug("NOT FOUND .search-result-single, give up search org detail info,throw NotFoundException");
                throw new NotFoundException();
            }
            crawlDomErrorNums += 1;
            if (!retry) {
                logger.error("NOT FOUND .search-result-single,天眼查出现未知页面失败，认为解析DOM出错，继续下一条，请人工检查日志! " + queryOrgName);

                throw new DOMParseException();
            }

            logger.warn("NOT FOUND .search-result-single,天眼查出现未知页面失败，本页面等待N秒后再次尝试解析'找到N条相关结果' " + queryOrgName);


            logger.error("******************************************* ERROR html *******************************************");
            logger.error(doc.getDocumentElement().getInnerHTML());
            return false;
        }

        return true;
    }

    /**
     * 解析检索页前5条信息，并返回公司名称匹配（当前名称或历史名称）的明细页url(也会保存检索页上的电话等信息)
     */
    @Override
    protected String fetchBasicInfo(Browser browser, Org org) {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return null;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        logger.debug("fetchBasicInfo start: " + queryOrgName);
        DOMDocument doc = null;
        String orgLink = null;
        try {
            doc = browser.getDocument();
            List<DOMElement> items = doc.findElements(By.className("search-result-single"));
            int iCount = items.size() > 5 ? 5 : items.size();
            for (int i = 0; i < iCount; i++) {
                DOMElement item = items.get(i);
                orgLink = fetchBasicInfoHtml(browser, org, item);

                if (orgLink != null) {
                    break;
                }
            }
            return orgLink;
        } catch (Throwable e) {
            logger.error("查询企业<" + queryOrgName + ">概要信息出错: ", e);
            logger.error("******************************************* ERROR html *******************************************");
            logger.error(doc.getDocumentElement().getInnerHTML());
            throw new DOMParseException();
        }
    }

    /**
     * 判断查询页是否搜索到结果
     */
    private boolean isNoData(DOMDocument doc) {
        List<DOMElement> imgs = doc.findElements(By.tagName("img"));
        for (DOMElement img : imgs) {
            String alt = img.getAttribute("alt");
            if ((alt != null) && ("无结果".equals(alt.trim()))) {
                logger.warn("NOT FOUND org,set org.crawlFlag=CrawlFlag.NOFOUND");
                return true;
            }
        }

        return false;
    }


    /**
     * 解析检索页信息，并返回明细页url(也会保存检索页上的电话等信息)
     */
    private String fetchBasicInfoHtml(Browser browser, Org org, DOMElement item) {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return null;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        String orgLink = null;
        DOMDocument doc = browser.getDocument();
        try {
            DOMElement titleLink = item.findElement(By.className("name"));
            if (titleLink == null) {
                logger.warn("NOT FOUND .name: " + queryOrgName);
                logger.warn("******************************************* ERROR html *******************************************");
                logger.warn(doc.getDocumentElement().getInnerHTML());
                throw new DOMParseException();
            }

            orgLink = titleLink.getAttribute("href");
            if (orgLink == null) {
                logger.warn("NOT FOUND .name.href: " + queryOrgName);
                logger.warn("******************************************* ERROR html *******************************************");
                logger.warn(doc.getDocumentElement().getInnerHTML());
                throw new DOMParseException();
            }

            String orgName = titleLink.getInnerHTML();
            String oldOrgName;
            String infoTel;
            orgName = SourceUtil.trimOrgName(orgName.trim().replaceAll("<em>", "").replaceAll("</em>", "").trim());

            //如果公司名称不匹配，查看是否能匹配上历史名称，如果还匹配不上，则返回，匹配上了，则记录历史名称
            if (!orgName.equals(queryOrgName)) {
                DOMElement match = item.findElement(By.className("match"));
                if (match == null) {
                    logger.warn("NOT FOUND .match: " + queryOrgName);
                    org.setContacts(new ArrayList());
                    return null;
                }
                List<DOMElement> spans = match.findElements(By.tagName("span"));
                if ((spans == null) || (spans.size() == 0)) {
                    logger.warn("NOT FOUND .match>span: " + queryOrgName);
                    org.setContacts(new ArrayList());
                    return null;
                }

                if (spans.get(0).getInnerText().trim().startsWith("历史名称")) {
                    oldOrgName = SourceUtil.trimOrgName(spans.get(1).getInnerHTML().trim().replaceAll("<em>", "").replaceAll("</em>", "").trim());
                    logger.debug("历史名称：" + oldOrgName);
                    if (!oldOrgName.equals(queryOrgName)) {
                        logger.warn("hisname NOT match! : " + queryOrgName);
                        return null;
                    }
                    logger.debug("orgBasicInfo FOUND oldOrgName: " + oldOrgName + ", newOrgName: " + orgName);

                    OrgHisName orgHisName = new OrgHisName();
                    orgHisName.setOrgName(oldOrgName);

                    org.getOldNames().add(orgHisName);
                }
            }

            DOMElement contactItem = item.findElement(By.className("contact"));
            List<DOMElement> scripts;
            if (contactItem != null) {
                List<DOMElement> cols = contactItem.findElements(By.className("col"));

                for (DOMElement col : cols) {
                    List<DOMElement> spans = col.findElements(By.tagName("span"));
                    if ((spans != null) && (spans.size() != 0)) {
                        Contact contact;
                        if (spans.get(0).getInnerText().trim().startsWith("联系电话")) {
                            infoTel = SourceUtil.trimTel(spans.get(1).getInnerText().trim());
                            if ((infoTel != null) && (!"".equals(infoTel)) && (infoTel.length() > 5)) {
                                contact = new Contact("总机");
                                contact.setTel(infoTel);
                                org.setContacts(mergeContact(contact, org.getContacts()));
                            }

                            scripts = col.findElements(By.tagName("script"));
                            if ((scripts != null) && (scripts.size() > 0)) {
                                DOMElement script = scripts.get(0);

                                Gson gson = new Gson();
                                Type type = new TypeToken<List<String>>() {}.getType();
                                List<String> tels = gson.fromJson(script.getInnerText(), type);
                                for (String tel : tels) {
                                    infoTel = StringTool.trimTel(tel);
                                    if ((infoTel != null) && (!"".equals(infoTel)) && (infoTel.length() > 5) &&
                                            (infoTel != null) && (!"".equals(infoTel)) && (infoTel.length() > 5)) {
                                        contact = new Contact("总机");
                                        contact.setTel(infoTel);
                                        org.setContacts(mergeContact(contact, org.getContacts()));
                                    }

                                }
                            }
                        } else if (spans.get(0).getInnerText().trim().startsWith("邮箱")) {
                            String email = StringTool.trimAll(spans.get(1).getInnerText());
                            if ((email != null) && (email.contains("@"))) {
                                contact = new Contact("总机");
                                contact.setEmail(email.toLowerCase());
                                org.setContacts(mergeContact(contact, org.getContacts()));
                            }


                            scripts = col.findElements(By.tagName("script"));
                            Gson gson;
                            if ((scripts != null) && (scripts.size() > 0)) {
                                DOMElement script = scripts.get(0);

                                gson = new Gson();
                                Type type = new TypeToken<List<String>>(){}.getType();
                                List<String> mails = gson.fromJson(script.getInnerText(), type);
                                for (String mail : mails) {
                                    email = StringTool.trimAll(mail);
                                    if ((email != null) && (email.contains("@"))) {
                                        contact = new Contact("总机");
                                        contact.setEmail(email.toLowerCase());
                                        org.setContacts(mergeContact(contact, org.getContacts()));
                                    }
                                }
                            }

                            logger.debug("*************** 联系人信息 *******************");
                            for (Contact c : org.getContacts()) {
                                logger.debug("name=" + c.getName() + ",tel=" + c.getTel() + ",mail=" + c.getEmail());
                            }
                        }
                    }
                }
            }

            org.setOrgName(orgName);
            logger.debug("找到的orgName: " + orgName + ",查询的orgName：" + queryOrgName);

            DOMElement header = item.findElement(By.className("header"));
            if (header == null) {
                logger.warn("NOT FOUND .header: " + queryOrgName);
                logger.warn("******************************************* ERROR html *******************************************");
                logger.warn(doc.getDocumentElement().getInnerHTML());
                throw new DOMParseException();
            }
            DOMElement tag = header.findElement(By.className("tag"));
            if (tag != null) {
                org.setStatus(tag.getInnerText().trim());
                logger.debug("状态：" + org.getStatus());
            }

            DOMElement info = header.findElement(By.className("info"));
            if (info != null) {
                List<DOMElement> eles = item.findElements(By.className("title"));
                for (DOMElement title : eles) {
                    String s = title.getInnerText().trim();
                    if (s.startsWith("法定代表人：")) {
                        DOMElement span = title.findElement(By.tagName("span"));
                        if (span == null) {
                            DOMElement a = title.findElement(By.tagName("a"));
                            if (a == null) {
                                logger.warn("NOT FOUND .title>span 法定代表人: " + queryOrgName);
                                logger.warn("******************************************* ERROR html *******************************************");
                                logger.warn(doc.getDocumentElement().getInnerHTML());
                                continue;
                            }
                            org.setPrincipal(a.getInnerText().trim());
                        } else {
                            org.setPrincipal(span.getInnerText().trim());
                        }
                        logger.debug("法定代表人：" + org.getPrincipal());
                    } else if (s.startsWith("注册资本：")) {
                        DOMElement span = title.findElement(By.tagName("span"));
                        if (span == null) {
                            logger.warn("NOT FOUND .title>span 注册资本: " + queryOrgName);
                            logger.warn("******************************************* ERROR html *******************************************");
                            logger.warn(doc.getDocumentElement().getInnerHTML());
                        } else {
                            org.setRegCapital(0);
                            org.setCapitalUnit(new CurrencyType("n"));
                            String regCapital = StringTool.trimAll(span.getInnerText());
                            String capitalUnit = "";
                            int iDecollator = regCapital.indexOf("万");
                            if (iDecollator != -1) {
                                String sRegCapital = regCapital.substring(0, iDecollator).trim();
                                capitalUnit = regCapital.substring(iDecollator + 1).trim();
                                regCapital = sRegCapital;
                            }
                            int iRegCapital = getRegCapitalInt(regCapital);
                            org.setRegCapital(iRegCapital);
                            org.setCapitalUnit(getCurrencyType(capitalUnit));
                            logger.debug("注册资本：" + org.getRegCapital() + org.getCapitalUnit().getId());
                        }
                    } else if (s.startsWith("注册时间：")) {
                        DOMElement span = title.findElement(By.tagName("span"));
                        if (span == null) {
                            logger.warn("NOT FOUND .title>span 注册时间: " + queryOrgName);
                            logger.warn("******************************************* ERROR html *******************************************");
                            logger.warn(doc.getDocumentElement().getInnerHTML());
                        } else {
                            org.setRegDate(DateTool.getDate(span.getInnerText().trim()));
                            logger.debug("注册时间：" + DateTool.getStringDate(org.getRegDate()));
                        }
                    }
                }
            }
            return orgLink;
        } catch (Throwable e) {
            logger.error("fetchBasicInfoHtml: 查询企业<" + queryOrgName + ">概要信息出错: ", e);
            logger.error("******************************************* ERROR html *******************************************");
            logger.error(doc.getDocumentElement().getInnerHTML());
        }
        return null;
    }


    @Override
    protected void fetchDetailInfo(Browser browser, Org org) {
        SourceOrg sourceOrg = getMySourceOrg(browser);
        if (sourceOrg == null) {
            logger.warn("NOT FOUND sourceOrg,url=" + browser.getURL());
            return;
        }
        String queryOrgName = sourceOrg.getQueryOrgName();

        DOMDocument doc = null;
        String infoTel;
        try {
            doc = browser.getDocument();

            DOMElement header = doc.findElement(By.className("company-header-block"));
            if (header == null) {
                logger.warn("NOT FOUND .company-header-block,sleep 5 seconds for waiting ajax loading data! " + queryOrgName);
                logger.warn("******************************************* ERROR html *******************************************");
                logger.warn(doc.getDocumentElement().getInnerHTML());

                Thread.sleep(5000L);
                doc = browser.getDocument();
                header = doc.findElement(By.className("company-header-block"));
                if (header == null) {
                    logger.warn("NOT FOUND .company-header-block: " + queryOrgName);
                    logger.error("******************************************* ERROR html *******************************************");
                    logger.error(doc.getDocumentElement().getInnerHTML());
                    throw new DOMParseException();
                }
            }

            DOMElement detail = header.findElement(By.className("detail"));
            if (detail == null) {
                logger.warn("NOT FOUND .company-header-block>.detail: " + queryOrgName);
                logger.error("******************************************* ERROR html *******************************************");
                logger.error(doc.getDocumentElement().getInnerHTML());
                throw new DOMParseException();
            }

            List<DOMElement> eles = detail.findElements(By.className("f0"));
            if ((eles == null) || (eles.size() == 0)) {
                logger.warn("NOT FOUND .company-header-block>.detail>.f0: " + queryOrgName);
                logger.error("******************************************* ERROR html *******************************************");
                logger.error(doc.getDocumentElement().getInnerHTML());
                throw new DOMParseException();
            }
            String address;
            for (DOMElement ele : eles) {
                if ((!"ElementNode".equals(ele.getNodeType().name())) || (!"DIV".equals(ele.getNodeName()))) {
                    break;
                }
                List<DOMElement> divs = ele.findElements(By.tagName("div"));
                for (DOMElement div : divs) {
                    List<DOMElement> spans = div.findElements(By.tagName("span"));
                    if ((spans == null) || (spans.size() == 0)) {
                        logger.warn("NOT FOUND .company-header-block>.detail>.f0 div span: " + queryOrgName);
                        logger.error("******************************************* ERROR html *******************************************");
                        logger.error(doc.getDocumentElement().getInnerHTML());
                        throw new DOMParseException();
                    }

                    DOMElement span = spans.get(0);
                    Contact contact;
                    if (span.getInnerText().trim().startsWith("电话")) {
                        infoTel = StringTool.trimTel(spans.get(1).getInnerText());
                        if ((infoTel != null) && (!"".equals(infoTel)) && (infoTel.length() > 5)) {
                            contact = new Contact("总机");
                            contact.setTel(infoTel);
                            org.setContacts(mergeContact(contact, org.getContacts()));
                        }
                        logger.debug("总机: " + infoTel);


                        if (spans.size() > 2) {
                            for (int i = 2; i < spans.size(); i++) {
                                span = spans.get(i);
                                List<DOMElement> scripts = div.findElements(By.tagName("script"));
                                if ((scripts != null) && (scripts.size() > 0)) {
                                    DOMElement script = scripts.get(0);

                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<String>>() {}.getType();
                                    List<String> tels = gson.fromJson(script.getInnerText(), type);
                                    for (String tel : tels) {
                                        infoTel = StringTool.trimTel(tel);
                                        if ((infoTel != null) && (!"".equals(infoTel)) && (infoTel.length() > 5)) {
                                            contact = new Contact("总机");
                                            contact.setTel(infoTel);
                                            org.setContacts(mergeContact(contact, org.getContacts()));
                                        }
                                    }
                                }
                            }
                        }
                    } else if (span.getInnerText().trim().startsWith("邮箱")) {
                        String email = StringTool.trimAll(((DOMElement) spans.get(1)).getInnerText());
                        if ((email != null) && (email.contains("@"))) {
                            contact = new Contact("总机");
                            contact.setEmail(email.toLowerCase());
                            org.setContacts(mergeContact(contact, org.getContacts()));
                        }


                        if (spans.size() > 2) {
                            for (int i = 2; i < spans.size(); i++) {
                                span = spans.get(i);
                                List<DOMElement> scripts = div.findElements(By.tagName("script"));
                                if ((scripts != null) && (scripts.size() > 0)) {
                                    DOMElement script = scripts.get(0);

                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<String>>() {}.getType();
                                    List<String> mails = gson.fromJson(script.getInnerText(), type);
                                    for (String mail : mails) {
                                        email = StringTool.trimAll(mail);
                                        if ((email != null) && (email.contains("@"))) {
                                            contact = new Contact("总机");
                                            contact.setEmail(email.toLowerCase());
                                            org.setContacts(mergeContact(contact, org.getContacts()));
                                        }
                                    }
                                }
                            }
                        }
                    } else if (span.getInnerText().trim().startsWith("网址")) {
                        String webUrl = null;
                        DOMElement web = div.findElement(By.tagName("a"));
                        if (web != null) {
                            webUrl = getWebUrl(web.getAttribute("href"));
                        }
                        org.setWeb(webUrl);
                        logger.debug("网址: " + webUrl);
                    } else if (span.getInnerText().trim().startsWith("地址")) {
                        if (spans.size() > 1) {
                            address = StringTool.trimAddress(spans.get(1).getAttribute("title"));
                            if ("".equals(address)) {
                                address = spans.get(1).getInnerText();
                            }
                        } else {
                            address = div.getInnerText();
                            int i = address.indexOf("地址：");
                            if (i != -1) {
                                address = address.substring(i + 3);
                            }
                        }

                        if ((address != null) && (!"".equals(address))) {
                            org.setAddress(address);
                        }
                        logger.debug("地址: " + address);
                    }
                }
            }

            DOMElement baseInfo = doc.findElement(By.id("_container_baseInfo"));
            if (baseInfo == null) {
                logger.warn("NOT FOUND by _container_baseInfo");
                return;
            }

            //法人
            DOMElement legalPerson = doc.findElement(By.className("humancompany"));
            DOMElement legalPersonInner = legalPerson.findElement(By.className("link-click"));
            org.setLegalPerson(legalPersonInner.getAttribute("title"));

            //注册资本
            DOMElement registeredCapital = (DOMElement) doc.findElement(By.className("tyc-num lh24")).getParent();
            org.setRegisteredCapital(registeredCapital.getAttribute("title"));

            List<DOMElement> tables = baseInfo.findElements(By.tagName("table"));

            for (DOMElement table : tables) {
                DOMElement tbody = table.findElement(By.tagName("tbody"));
                if (tbody != null) {
                    List<DOMElement> trs = tbody.findElements(By.tagName("tr"));
                    for (DOMElement tr : trs) {
                        List<DOMElement> tds = tr.findElements(By.tagName("td"));
                        for (int i = 0; i < tds.size(); i++) {
                            if (i % 2 == 0) {
                                String tdTitle = tds.get(i).getInnerText().trim();
                                if (!StringUtils.isEmpty(tdTitle) && i + 1 < tds.size()) {
                                    String sTmp = tds.get(i+1).getInnerText().trim();
                                    if ("统一社会信用代码".equals(tdTitle)) {
                                        org.setUscd(sTmp);
                                        logger.debug("uscd: " + sTmp);
                                    } else if ("组织机构代码".equals(tdTitle)) {
                                        org.setOrgCode(sTmp);
                                        logger.debug("orgCode: " + sTmp);
                                    } else if ("工商注册号".equals(tdTitle)) {
                                        org.setBusiLicenseCode(sTmp);
                                        logger.debug("busiLicenseCode: " + sTmp);
                                    } else if ("行业".equals(tdTitle)) {
                                        org.setIndustryType(sTmp);
                                        logger.debug("industryType: " + sTmp);
                                    } else if (!"注册时间".equals(tdTitle)) {
                                        if ("公司类型".equals(tdTitle)) {
                                            org.setOrgType(getOrgType(sTmp));
                                            logger.debug("orgType: " + sTmp);
                                        } else if ("登记机关".equals(tdTitle)) {
                                            org.setRegOrganization(sTmp);
                                            logger.debug("regOrganization: " + sTmp);
                                        } else if (!tdTitle.contains("注册地址")) {
                                            if ("经营范围".equals(tdTitle)) {
                                                org.setBusiScope(sTmp);
                                                logger.debug("busiScope: " + sTmp);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            logger.error("查询企业<" + queryOrgName + ">详情信息出错: ", e);
            logger.warn("******************************************* ERROR html *******************************************");
            logger.warn(doc.getDocumentElement().getInnerHTML());
            throw new DOMParseException();
        }
    }

    @Override
    protected String getBasicUrl(String queryOrgName, long queryTime) {
        String url = null;
        try {
            String param = URLEncoder.encode(queryOrgName, "UTF-8");
            url = homeUrl + "/search?key=" + param + "&et=" + queryTime;
        } catch (Throwable e) {
            logger.error("", e);
        }
        return url;
    }

    @Override
    public String getLoginUrl() {
        return "https://www.tianyancha.com/login";
    }
}
