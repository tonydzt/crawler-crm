package com.kasuo.crawler.domain.source;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.permission.User;
import com.kasuo.crawler.util.DateTool;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author douzhitong
 * @date 18/11/17
 */


public class SourceInput extends GenericEntity
{
        private static final long serialVersionUID = 1L;
        protected SourceInputLog inputLog = null;
        protected Org org = null;
        protected String orgName = null;
        protected int priorityWeight = 1;
        protected SourceBatchPriority priority = new SourceBatchPriority("1");
        protected SourceHandleStatus status = new SourceHandleStatus("0");
        protected String memo = null;
        protected String tmAddress = null;
        protected Date assignedTime = null;
        protected Date contactTime = null;
        protected User assignedUser = null;
        protected ContactStatus contactStatus = new ContactStatus("0");
        protected Set<SourceInputTMDetail> details = new HashSet();
        protected ValidFlag validFlag = new ValidFlag("1");
        protected String crawler = null;
        protected Date crawlTime = null;
        protected String chanceTitle = null;
        protected int agentHandleFlag = 0;
        protected CrawlOrgFlag crawlOrgFlag = new CrawlOrgFlag("1");

        @Override
        public String toString()
        {
        return "id=" + this.id + ",createTime=" + DateTool.getStringDateTime(this.createTime) + ",orgName=" + this.orgName + ",tmAddress=" + this.tmAddress;
        }

        public SourceInput() {}

        public SourceInput(String id)
        {
        this.id = id;
        }

        public SourceInputLog getInputLog()
        {
        return this.inputLog;
        }

        public void setInputLog(SourceInputLog inputLog)
        {
        this.inputLog = inputLog;
        }

        public Org getOrg()
        {
        return this.org;
        }

        public void setOrg(Org org)
        {
        this.org = org;
        }

        public ValidFlag getValidFlag()
        {
        return this.validFlag;
        }

        public void setValidFlag(ValidFlag validFlag)
        {
        this.validFlag = validFlag;
        }

        public CrawlOrgFlag getCrawlOrgFlag()
        {
        return this.crawlOrgFlag;
        }

        public void setCrawlOrgFlag(CrawlOrgFlag crawlOrgFlag)
        {
        this.crawlOrgFlag = crawlOrgFlag;
        }

        public Date getAssignedTime()
        {
        return this.assignedTime;
        }

        public void setAssignedTime(Date assignedTime)
        {
        this.assignedTime = assignedTime;
        }

        public String getOrgName()
        {
        return this.orgName;
        }

        public void setOrgName(String orgName)
        {
        this.orgName = orgName;
        }

        public int getPriorityWeight()
        {
        return this.priorityWeight;
        }

        public void setPriorityWeight(int priorityWeight)
        {
        this.priorityWeight = priorityWeight;
        }

        public SourceBatchPriority getPriority()
        {
        return this.priority;
        }

        public void setPriority(SourceBatchPriority priority)
        {
        this.priority = priority;
        }

        public SourceHandleStatus getStatus()
        {
        return this.status;
        }

        public void setStatus(SourceHandleStatus status)
        {
        this.status = status;
        }

        public String getMemo()
        {
        return this.memo;
        }

        public void setMemo(String memo)
        {
        this.memo = memo;
        }

        public String getTmAddress()
        {
        return this.tmAddress;
        }

        public void setTmAddress(String tmAddress)
        {
        this.tmAddress = tmAddress;
        }

        public User getAssignedUser()
        {
        return this.assignedUser;
        }

        public void setAssignedUser(User assignedUser)
        {
        this.assignedUser = assignedUser;
        }

        public ContactStatus getContactStatus()
        {
        return this.contactStatus;
        }

        public void setContactStatus(ContactStatus contactStatus)
        {
        this.contactStatus = contactStatus;
        }

        public Set<SourceInputTMDetail> getDetails()
        {
        return this.details;
        }

        public void setDetails(Set<SourceInputTMDetail> details)
        {
        this.details = details;
        }

        public Date getContactTime()
        {
        return this.contactTime;
        }

        public void setContactTime(Date contactTime)
        {
        this.contactTime = contactTime;
        }

        public String getCrawler()
        {
        return this.crawler;
        }

        public void setCrawler(String crawler)
        {
        this.crawler = crawler;
        }

        public Date getCrawlTime()
        {
        return this.crawlTime;
        }

        public void setCrawlTime(Date crawlTime)
        {
        this.crawlTime = crawlTime;
        }

        public String getChanceTitle()
        {
        return this.chanceTitle;
        }

        public void setChanceTitle(String chanceTitle)
        {
        this.chanceTitle = chanceTitle;
        }

        public int getAgentHandleFlag()
        {
        return this.agentHandleFlag;
        }

        public void setAgentHandleFlag(int agentHandleFlag)
        {
        this.agentHandleFlag = agentHandleFlag;
        }
        }

