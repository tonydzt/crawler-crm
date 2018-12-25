//package com.kasuo.crawler.service.crm;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.kasuo.crawler.domain.crm.SourceInputStat;
//import com.kasuo.crawler.domain.datadictionary.CrawlFlag;
//import com.kasuo.crawler.domain.datadictionary.CrawlOrgFlag;
//import com.kasuo.crawler.domain.datadictionary.SourceHandleStatus;
//import com.kasuo.crawler.domain.datadictionary.ValidFlag;
//import com.kasuo.crawler.domain.org.Org;
//import com.kasuo.crawler.domain.org.OrgHisName;
//import com.kasuo.crawler.domain.source.SourceInput;
//import com.kasuo.crawler.domain.source.SourceInputTMDetail;
//import com.kasuo.crawler.domain.source.SourceUtil;
//import com.kasuo.crawler.domain.trademark.entity.Trademark;
//import com.kasuo.crawler.service.TestService;
//import com.kasuo.crawler.util.DateTool;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class SourceInputService {
//
//  private static final Logger logger = LoggerFactory.getLogger(TestService.class);
//
//  public SourceInputStat saveSourceInputFromBaitu(String inputLogId, Map<String, SourceInput> inputMap, long times)
//  {
//    logger.debug("fetchBaituVerifyData: saveSourceInputFromBaitu");
//
//    int usableNums = 0;
//    int current = 0;
//
//    Date minApplyDate = null;
//    Date maxApplyDate = null;
//    SourceInputStat stat = new SourceInputStat();
//    int totalTmNums = 0;
//    int validTmNums = 0;
//
////    SourceInputLog log = (SourceInputLog)getEntity(inputLogId, SourceInputLog.class);
//
//    logger.debug("saveSourceInputFromBaitu: for loop, size=" + inputMap.entrySet().size());
//    for (Map.Entry<String, SourceInput> ele : inputMap.entrySet())
//    {
//      SourceInput input = (SourceInput)ele.getValue();
//      try
//      {
//        Set<SourceInputTMDetail> details = input.getDetails();
//        List<SourceInputTMDetail> oldDetails = new ArrayList();
//        for (SourceInputTMDetail detail : details)
//        {
//          Trademark tm = detail.getTm();
//          if (tm != null)
//          {
//            totalTmNums++;
//            if (input.getValidFlag().isValid()) {
//              validTmNums++;
//            }
//            Date applyDate = tm.getApplyDate();
//            if (applyDate != null)
//            {
//              if (minApplyDate != null)
//              {
//                if (applyDate.before(minApplyDate)) {
//                  minApplyDate = applyDate;
//                }
//              }
//              else {
//                minApplyDate = applyDate;
//              }
//              if (maxApplyDate != null)
//              {
//                if (applyDate.after(maxApplyDate)) {
//                  maxApplyDate = applyDate;
//                }
//              }
//              else {
//                maxApplyDate = applyDate;
//              }
//            }
//          }
//        }
//        if (oldDetails.size() == details.size())
//        {
//          logger.warn("saveSourceInputFromBaitu: FOUND ������������detail,���������SourceInput,orgName=" + input.getOrgName());
//        }
//        else
//        {
//          details.removeAll(oldDetails);
//
//          String sql = "from SourceInput input where input.inputLog.id=? and input.orgName=?";
//          SourceInput oldInput = (SourceInput)dao().queryFirst(sql, new Object[] { inputLogId, input.getOrgName() });
//
//          saveSourceInput(inputLogId, input, stat);
//          if ((oldInput == null) && (input.getValidFlag().isValid())) {
//            usableNums++;
//          }
//        }
//        logger.debug("saveSourceInputFromBaitu: for loop, current=" + current);
//        current++;
//      }
//      catch (Throwable e)
//      {
//        logger.error("saveSourceInputFromBaitu: for loop error! inputLogId=" + inputLogId + ",orgName=" + input.getOrgName(), e);
//      }
//    }
//    logger.debug("save SourceInput: for loop end");
//
////    log.setStatus(new SourceBatchStatus("6"));
////    log.setUsableNums(log.getUsableNums() + usableNums);
////    log.setUnassignedNums(log.getUnassignedNums() + usableNums);
////    log.setMinApplyDate(minApplyDate);
////    log.setMaxApplyDate(maxApplyDate);
////
////    dao().update(log);
//
////    String msg = "<font color='red'><b>������������</b></font>" + log.getBatchNo() + "<br><br>";
////    msg = msg + "<font color='red'><b>������������</b></font>" + (log.getFileName() == null ? "" : log.getFileName()) + "<br><br>";
////    msg = msg + "<font color='red'><b>������������������������</b></font>" + log.getTotalNums() + "���<br><br>";
////    msg = msg + "<font color='red'><b>������������</b></font>" + inputMap.entrySet().size() + "���<br><br>";
////    msg = msg + "<font color='red'><b>������������</b></font>" + log.getUsableNums() + "���<br><br>";
////    msg = msg + "<font color='red'><b>���������������</b></font>" + DateTool.getStringDate(minApplyDate) + " ~ " + DateTool.getStringDate(maxApplyDate) + "<br><br>";
////    msg = msg + "<font color='red'><b>���������������������������</b></font>" + times + "���<br>";
////    if (!NoticeTool.sendMail(0, "���������<" + log.getBatchNo() + ">������������������������", msg))
////    {
////      Object paramMap = new HashMap();
////      ((Map)paramMap).put("batchNo", log.getBatchNo());
////      ((Map)paramMap).put("srcBatchNo", log.getFileName() == null ? "" : log.getFileName());
////      ((Map)paramMap).put("usableNums", log.getUsableNums());
////      NoticeTool.sendSMS(0, "SMS_114840001", (Map)paramMap);
////    }
//    stat.setTotalTmNums(totalTmNums);
//    stat.setValidTmNums(validTmNums);
//    stat.setValidOrgNums(usableNums);
//    return stat;
//  }
//
//  public boolean saveSourceInput(String inputLogId, SourceInput input, SourceInputStat stat)
//  {
////    SourceInputLog log = (SourceInputLog)getEntity(inputLogId, SourceInputLog.class);
//
//    logger.debug("saveSourceInput: for only one input, input.orgName=" + input.getOrgName());
//    try
//    {
////      input.setInputLog(log);
//
//      Org org = null;
//      boolean needCrawlOrgFlag = false;
//      Set<SourceInputTMDetail> details = input.getDetails();
//
//      String sql = "from SourceInput input where input.inputLog.id=? and input.orgName=?";
//      SourceInput oldInput = (SourceInput)dao().queryFirst(sql, new Object[] { inputLogId, input.getOrgName() });
//      CustomerCache customerCache;
//      if (oldInput != null)
//      {
//        logger.warn("saveSourceInput: found old input,logId=" + inputLogId + ",input.orgName=" + input.getOrgName() + ",oldInputId=" + oldInput.getId());
//        org = oldInput.getOrg();
//        input = oldInput;
//      }
//      else
//      {
//        if (input.getValidFlag().isValid())
//        {
//          stat.setNewTmNums(stat.getNewTmNums() + details.size());
//
//          org = (Org)dao().queryFirst("from Org org where org.orgName=?", new Object[] { input.getOrgName() });
//          OrgHisName hisName = null;
//          if (org == null)
//          {
//            hisName = (OrgHisName)dao().queryFirst("from OrgHisName o join fetch o.org where o.orgName=?", new Object[] { input.getOrgName() });
//            if (hisName != null)
//            {
//              org = hisName.getOrg();
//              if (org.getValidPhoneNums() + org.getValidTelNums() == 0)
//              {
//                logger.debug("saveSourceInput: found old Org,orgId=" + org.getId() + ",no valid tel,need recrawl");
//                needCrawlOrgFlag = true;
//              }
//              if (((org.getAssignedUser() == null) || (org.getAssignedUser().isPublicUser())) && (
//                (org.getCrawlTime() == null) || (DateTool.dateBefore(org.getCrawlTime(), DateTool.addDate(-90)))))
//              {
//                logger.debug("saveSourceInput: found old Org,orgId=" + org.getId() + ",tel info passed 3 months and no assigned,need recrawl");
//                needCrawlOrgFlag = true;
//              }
//            }
//            else
//            {
//              needCrawlOrgFlag = true;
//              org = new Org();
//              org.setOrgName(input.getOrgName());
//              org.setTmAddress(input.getTmAddress());
//              org.setCrawlFlag(new CrawlFlag("1"));
//              save(org);
//              logger.debug("saveSourceInput: save org,orgId=" + org.getId() + ",orgName=" + org.getOrgName());
//
//              customerCache = CustomerCache.getInstance();
//              customerCache.addCustomer(org);
//            }
//          }
//          else
//          {
//            if (org.getValidPhoneNums() + org.getValidTelNums() == 0)
//            {
//              logger.debug("saveSourceInput: found old Org,orgId=" + org.getId() + ",no valid tel,need recrawl");
//              needCrawlOrgFlag = true;
//            }
//            if (((org.getAssignedUser() == null) || (org.getAssignedUser().isPublicUser())) && (
//              (org.getCrawlTime() == null) || (DateTool.dateBefore(org.getCrawlTime(), DateTool.addDate(-90)))))
//            {
//              logger.debug("saveSourceInput: found old Org,orgId=" + org.getId() + ",tel info passed 3 months and no assigned,need recrawl");
//              needCrawlOrgFlag = true;
//            }
//          }
//          input.setOrg(org);
//          if (needCrawlOrgFlag)
//          {
//            if (SourceUtil.orgNameIsRegNum(org.getOrgName()))
//            {
//              input.setCrawlOrgFlag(new CrawlOrgFlag("0"));
//            }
//            else
//            {
//              input.setCrawlOrgFlag(new CrawlOrgFlag("1"));
//              stat.setNewOrgNums(stat.getNewOrgNums() + 1);
//            }
//          }
//          else
//          {
//            input.setCrawlOrgFlag(new CrawlOrgFlag("0"));
//            input.setStatus(new SourceHandleStatus("2"));
//            setInputPriority(input);
//          }
//        }
//        save(input);
//      }
//      if (input.getValidFlag().isValid())
//      {
//        for (SourceInputTMDetail detail : details)
//        {
//          Trademark tm = detail.getTm();
//          String regNum = tm.getRegNum();
//          int cls = tm.getCls();
//          if (regNum == null)
//          {
//            logger.warn("saveSourceInput: regNum is null!");
//          }
//          else
//          {
//            Trademark oldTm = tmService.queryByRegNumAndCls(regNum, cls);
//            if (oldTm == null)
//            {
//              logger.debug("saveSourceInput: NOT FOUND <regNum=" + regNum + ",cls=" + cls + "> , create new tm");
//              tm.setOrg(org);
//              save(tm);
//              oldTm = tm;
//              logger.debug("saveSourceInput: save newTM,newTM.id=" + tm.getId() + ",newTM.orgId=" + org.getId() + ",newTM.orgName=" + tm.getOrgName());
//            }
//            else
//            {
//              logger.debug("saveSourceInput: FOUND <regNum=" + regNum + ",cls=" + cls + "> , set detail.tm=oldTM and oldTM.org=org(if oldTM.org is null)");
//              detail.setTm(oldTm);
//              if (tm.getSourceType().isGovInside()) {
//                oldTm.setSourceType(tm.getSourceType());
//              }
//              if ((oldTm.getOrg() != null) && (!oldTm.getOrg().getId().equals(org.getId())))
//              {
//                logger.error("saveSourceInput: found oldTm.org != new org,set oldTm.org = new org! oldTm.orgId=" + oldTm.getOrg().getId() + ",new org.Id=" + org.getId() + ",oldTm.lastModifyTime=" + DateTool.getStringDateTime(oldTm.getLastModifyTime()) + ",oldTm.orgName=" +
//                  oldTm.getOrgName() + ",new OrgName=" + org.getOrgName());
//                oldTm.setOrg(org);
//              }
//              else if (oldTm.getOrg() == null)
//              {
//                oldTm.setOrg(org);
//              }
//              dao().update(oldTm);
//            }
//            detail.setInput(input);
//            if (oldInput != null)
//            {
//              sql = "from SourceInputTMDetail detail where detail.input.id=? and detail.tm.id=?";
//              SourceInputTMDetail oldDetail = (SourceInputTMDetail)dao().queryFirst(sql, new Object[] { oldInput.getId(), detail.getTm().getId() });
//              if (oldDetail == null) {
//                save(detail);
//              }
//            }
//            else
//            {
//              save(detail);
//            }
//            if (tm.getRelatedTms() != null)
//            {
//              int i = 0;
//              for (Trademark relatedTm : tm.getRelatedTms())
//              {
//                i++;
//
//                Trademark oldRelatedTm = tmService.queryByRegNumAndCls(relatedTm.getRegNum(), relatedTm.getCls());
//                SourceInputTMQuota quota = new SourceInputTMQuota();
//                quota.setDetail(detail);
//                quota.setInc(i);
//                quota.setInputLog(log);
//                quota.setTm(oldRelatedTm);
//                quota.setRegNum(relatedTm.getRegNum());
//                quota.setCls(relatedTm.getCls());
//                save(quota);
//              }
//            }
//          }
//        }
//        if ((org != null) && (oldInput == null) &&
//          (input.getValidFlag().isValid()))
//        {
//          if ((org.getStatus() != null) && ((org.getStatus().contains("������")) || (org.getStatus().contains("������"))))
//          {
//            input.setValidFlag(new ValidFlag("0"));
//            input.setCrawlOrgFlag(new CrawlOrgFlag("0"));
//            dao().update(input);
//
////            log.setUsableNums(log.getUsableNums() - 1);
////            log.setUnassignedNums(log.getUnassignedNums() - 1);
//          }
//          else if (!needCrawlOrgFlag)
//          {
//            if (org.getValidPhoneNums() > 0) {
//              log.setCrawlPhoneNums(log.getCrawlPhoneNums() + 1);
//            } else if (org.getValidTelNums() > 0) {
//              log.setCrawlTelNums(log.getCrawlTelNums() + 1);
//            }
//          }
//          dao().update(log);
//        }
//      }
//    }
//    catch (Throwable e)
//    {
//      logger.error("saveSourceInput: for loop error, " + input.toString(), e);
//      return false;
//    }
//    return true;
//  }
//
//  public void setInputPriority(SourceInput input)
//  {
//    int phone = 0;
//    int tel = 0;
//
//    input.setPriorityWeight(1);
//    input.setPriority(new Priority("1"));
//
//    Org org = input.getOrg();
//    if (org == null) {
//      return;
//    }
//    phone = org.getValidPhoneNums();
//    tel = org.getValidTelNums();
//    if ((input.getOrg().getAssignedUser() != null) && (!input.getOrg().getAssignedUser().isPublicUser()))
//    {
//      if (input.getOrg().getPurpose().isPurpose())
//      {
//        input.setPriorityWeight(41);
//        input.setPriority(new Priority("4"));
//      }
//      else
//      {
//        input.setPriorityWeight(31);
//        input.setPriority(new Priority("3"));
//      }
//    }
//    else if (phone > 0)
//    {
//      input.setPriorityWeight(21);
//      input.setPriority(new Priority("3"));
//    }
//    else if (tel > 0)
//    {
//      input.setPriorityWeight(11);
//      input.setPriority(new Priority("2"));
//    }
//  }
//}
