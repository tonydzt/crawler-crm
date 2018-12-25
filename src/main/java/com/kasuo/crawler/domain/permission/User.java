package com.kasuo.crawler.domain.permission;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.Gender;
import com.kasuo.crawler.domain.datadictionary.UserStatus;

import java.util.HashSet;
import java.util.Set;


public class User extends GenericEntity {
    public static final String PUBLIC_USER_ID = "8abce6cf574c31a101574c3576de0001";
    public static final String IT_USER_ID = "40288a03575b580e01575b6acc88000b";
    public static final String ADMIN_USER_ID = "40288a03575b580e01575b6881c60006";
    private static final long serialVersionUID = 1L;
    private String password;
    private String chiName;
    private UserStatus status;
    private Gender gender;
    private int delFlag = 0;
    private long todayAssignedChanceNums = 0L;
    private long todayUncontactChanceNums = 0L;
    private long totalUncontactChanceNums = 0L;
    private Set<Role> roles = new HashSet();
    private String phoneNum = null;
    private String phoneIp = null;
    private boolean isTelSaler = false;
    private boolean isTradeTyping = false;
    private int innerAccess = 1;
    private int outerAccess = 0;
    private int crawlDuty = 0;
    private int online = 0;


    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelf(String userId) {
        if (id.equals(userId)) {
            return true;
        }
        return false;
    }

    public boolean isPublicUser() {
        if (id.equals("8abce6cf574c31a101574c3576de0001")) {
            return true;
        }
        return false;
    }

    public boolean isAdminUser() {
        if (id.equals("40288a03575b580e01575b6881c60006")) {
            return true;
        }
        return false;
    }

    public boolean isITUser() {
        if (id.equals("40288a03575b580e01575b6acc88000b")) {
            return true;
        }
        return false;
    }

    public boolean isRole(String roleId) {
        if (roles == null) {
            return false;
        }
        for (Role role : roles) {
            if (role.getId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTodayAssignedChanceNums() {
        return todayAssignedChanceNums;
    }

    public void setTodayAssignedChanceNums(long todayAssignedChanceNums) {
        this.todayAssignedChanceNums = todayAssignedChanceNums;
    }

    public long getTodayUncontactChanceNums() {
        return todayUncontactChanceNums;
    }

    public void setTodayUncontactChanceNums(long todayUncontactChanceNums) {
        this.todayUncontactChanceNums = todayUncontactChanceNums;
    }

    public long getTotalUncontactChanceNums() {
        return totalUncontactChanceNums;
    }

    public void setTotalUncontactChanceNums(long totalUncontactChanceNums) {
        this.totalUncontactChanceNums = totalUncontactChanceNums;
    }

    public String getChiName() {
        return chiName;
    }

    public void setChiName(String chiName) {
        this.chiName = chiName;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneIp() {
        return phoneIp;
    }

    public void setPhoneIp(String phoneIp) {
        this.phoneIp = phoneIp;
    }

    public boolean isTelSaler() {
        return isTelSaler;
    }

    public void setTelSaler(boolean isTelSaler) {
        this.isTelSaler = isTelSaler;
    }

    public boolean isTradeTyping() {
        return isTradeTyping;
    }

    public void setTradeTyping(boolean isTradeTyping) {
        this.isTradeTyping = isTradeTyping;
    }

    public int getInnerAccess() {
        return innerAccess;
    }

    public void setInnerAccess(int innerAccess) {
        this.innerAccess = innerAccess;
    }

    public int getOuterAccess() {
        return outerAccess;
    }

    public void setOuterAccess(int outerAccess) {
        this.outerAccess = outerAccess;
    }

    public int getCrawlDuty() {
        return crawlDuty;
    }

    public void setCrawlDuty(int crawlDuty) {
        this.crawlDuty = crawlDuty;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}
