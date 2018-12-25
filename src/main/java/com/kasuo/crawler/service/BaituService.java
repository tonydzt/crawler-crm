//package com.kasuo.crawler.service;
//
//import com.kasuo.crawler.codec.BaituEncoder;
//import com.kasuo.crawler.dao.BaituDao;
//import com.kasuo.crawler.domain.datadictionary.TrademarkStatus;
//import com.kasuo.crawler.domain.source.SourceUtil;
//import com.kasuo.crawler.domain.trademark.entity.Trademark;
//import com.kasuo.crawler.util.DateTool;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Service;
//
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author douzhitong
// * @date 18/11/17
// */
//@Service
//public class BaituService {
//
//    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
//
//    @Autowired
//    private BaituDao baituDao;
//
//    @Autowired
//    private BaituEncoder baituEncoder;
//
//    @Autowired
//    @Qualifier("jdbcTemplateBt")
//    private JdbcTemplate jdbcTemplate;
//
//    public void queryBaituEncode(BaituEncoder baituEncoder) {
//
//        List<Map<String,Object>> resultMap = baituDao.getDecodeMap();
//
//        for (Map<String,Object> map : resultMap) {
//
//            String encode = patch2pad(map.get("regNumLen")) + patch2pad(map.get("lastIndex")) + map.get("spec") + map.get("code");
//            String decode = patch2pad(map.get("regNumLen")) + patch2pad(map.get("lastIndex")) + map.get("spec") + map.get("encode");
//
//            baituEncoder.encodeMap.put(encode, map.get("encode").toString());
//            baituEncoder.decodeMap.put(decode, map.get("code").toString());
//        }
//    }
//
//    public void queryBaituEncode2(BaituEncoder baituEncoder) {
//
//        List<Map<String,Object>> resultMap = baituDao.getDecodeMap2();
//
//        for (Map<String,Object> map : resultMap) {
//
//            String encode = patch2pad(map.get("firstIndex")) + map.get("code");
//            String decode = patch2pad(map.get("firstIndex")) + map.get("encode");
//
//            baituEncoder.encodeMap2.put(encode, map.get("encode").toString());
//            baituEncoder.decodeMap2.put(decode, map.get("code").toString());
//        }
//    }
//
//    private String patch2pad(Object i)
//    {
//        String s = i.toString();
//        if (s.length() == 1) {
//            s = "0" + s;
//        }
//        return s;
//    }
//
//    public List<Trademark> queryVerifyDataByApplyDate(Date startApplyDate, Date endApplyDate)
//    {
//        logger.error("queryVerifyDataByApplyDate: startApplyDate=" + DateTool.getStringDate(startApplyDate) + ",endApplyDate=" + DateTool.getStringDate(endApplyDate));
//
//        String startDate = DateTool.getTightStringDate(startApplyDate);
//        String endDate = DateTool.getTightStringDate(endApplyDate);
//
//        baituEncoder.getInstance();
//
//        List<Trademark> list = new ArrayList<>();
//        for (int i = 1; i <= 45; i++)
//        {
//            String cls = String.valueOf(i);
//            if (cls.length() == 1) {
//                cls = "0" + cls;
//            }
//            final int iCls = Integer.parseInt(cls);
//
//            String sql = "select fTMCHIN,fTMENG,fTMPY,fTMHEAD,fTMSZ,fSQR1,fAddr,fSQDATE,fCSDATE,fZCDATE,fJZDATE,fBGUserID,fDLZZ,fBZ,fTMID from QSTMGood.dbo.tTM" + cls;
//            sql = sql + " where fSQDATE>='" + startDate + "' and fSQDATE<='" + endDate + "' and fBGUserID='101'";
//
//            List<Trademark> rsList = jdbcTemplate.query(sql, (RowMapper) (rs, rowNum) -> {
//                Trademark tm = new Trademark();
//                String value = null;
//                String regNum = null;
//                try
//                {
//                    InputStream in = rs.getBinaryStream(15);
//
//                    byte[] buf = new byte[32];
//                    int len = in.read(buf);
//                    if (len > 0) {
//                        regNum = baituEncoder.getRegNum(byte2hex(Arrays.copyOf(buf, len)));
//                    } else {
//                        logger.error("get fTMID error! getBinaryStream len=" + len);
//                    }
//                }
//                catch (Throwable e)
//                {
//                    logger.error("get fTMID error!", e);
//                }
//                tm.setRegNum(regNum);
//                tm.setCls(iCls);
//
//                value = rs.getString(1);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: name containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setName(value);
//
//                value = rs.getString(2);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: nameEng containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setNameEng(value);
//
//                value = rs.getString(3);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: spell containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setSpell(value);
//
//                value = rs.getString(4);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: header containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setHeader(value);
//
//                value = rs.getString(5);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: number containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setNumber(value);
//
//                value = rs.getString(6);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: orgName containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setOrgName(SourceUtil.trimOrgName(value));
//
//                value = rs.getString(7);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: tmAddress containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setTmAddress(SourceUtil.trimAddress(value));
//
//                tm.setApplyDate(getDate(rs.getString(8)));
//                tm.setReviewDate(getDate(rs.getString(9)));
//                tm.setApprovedDate(getDate(rs.getString(10)));
//                tm.setExpiryDate(getDate(rs.getString(11)));
//                tm.setStatus(getTrademarkStatus(rs.getString(12)));
//
//                value = rs.getString(13);
//                if (containErrorString(value))
//                {
//                    value = "[未知]";
//                    logger.warn("queryVerifyDataByApplyDate: agentName containErrorString,regNum=" + regNum + ",cls=" + iCls);
//                }
//                tm.setAgentName(SourceUtil.trimOrgName(value));
//
//                logger.debug("rowNum=" + rowNum + ",regNum=" + regNum + ",cls=" + iCls + "," + tm.getNameShowAll() + "," + tm.getOrgName());
//                return tm;
//            });
//            list.addAll(rsList);
//        }
//        return list;
//    }
//
//    private TrademarkStatus getTrademarkStatus(String s)
//    {
//        if (s == null) {
//            return new TrademarkStatus("0");
//        }
//        s = s.trim();
//        if (s.equals("100")) {
//            return new TrademarkStatus("5");
//        }
//        if (s.equals("101")) {
//            return new TrademarkStatus("1");
//        }
//        if (s.equals("102")) {
//            return new TrademarkStatus("4");
//        }
//        if (s.equals("103")) {
//            return new TrademarkStatus("3");
//        }
//        if (s.equals("104")) {
//            return new TrademarkStatus("2");
//        }
//        return new TrademarkStatus("0");
//    }
//
//    private Date getDate(String s)
//    {
//        try
//        {
//            if (s == null) {
//                return null;
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            return sdf.parse(s);
//        }
//        catch (Throwable e) {}
//        return null;
//    }
//
//    private boolean containErrorString(String s)
//    {
//        if (s == null) {
//            return false;
//        }
//        s = s.trim();
//        if (s.startsWith("\\x")) {
//            return true;
//        }
//        return false;
//    }
//
//    private String byte2hex(byte[] buffer)
//    {
//        String h = "";
//        for (int i = 0; i < buffer.length; i++)
//        {
//            String temp = Integer.toHexString(buffer[i] & 0xFF);
//            if (temp.length() == 1) {
//                temp = "0" + temp;
//            }
//            h = h + temp;
//        }
//        return h.toUpperCase();
//    }
//}
