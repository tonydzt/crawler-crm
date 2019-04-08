package com.kasuo.crawler.service;

import com.kasuo.crawler.common.HttpResult;
import com.kasuo.crawler.common.Pagable;
import com.kasuo.crawler.config.ExcelConfig;
import com.kasuo.crawler.config.HttpConfig;
import com.kasuo.crawler.dao.TrademarkDao;
import com.kasuo.crawler.dao.mybatis.*;
import com.kasuo.crawler.domain.*;
import com.kasuo.crawler.domain.vo.AssignVo;
import com.kasuo.crawler.domain.vo.TrademarkExportVO;
import com.kasuo.crawler.util.HttpUtils;
import com.kasuo.crawler.util.JsonUtils;
import com.kasuo.crawler.util.excel.ExcelData;
import com.kasuo.crawler.util.excel.ExportExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 1.手机号优先
 * 2.老客户新机会优先分配给负责的业务员
 * 3.可以根据地域分配
 * 4.每个人分配的数据量可以不同
 *
 * @author douzhitong
 * @date 2018/12/22
 */
@Service
public class AssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentService.class);

    private static List<String> exportExcelHeads = Lists.newArrayList("客户名称(必填)", "电话", "地址", "跟进状态", "下次跟进时间", "备注", "商标名称	", "注册号", "申请时间", "类别", "负责人", "联系人姓名", "联系人电话", "联系人手机", "机会类型");

    @Autowired
    private ExcelConfig excelConfig;

    @Autowired
    private CrawlerConfigService crawlerConfigService;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private TrademarkDao trademarkDao;

    @Autowired
    private ExcelStatusDao excelStatusDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private TelDao telDao;

    @Autowired
    private MobileDao mobileDao;

    public void assignOrg(Org org, Long trademarkId) {

        if (org.getHasContact() != null && org.getHasContact()) {
            String date = crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_DATE);
            Assignment assignment = assignmentDao.findByOrgIdAndDate(org.getId(), date);
            Trademark trademark = trademarkDao.find(trademarkId);

            if (assignment == null) {

                Contact contact = getContact(org.getId());

                assignment = new Assignment();
                assignment.setOrgId(org.getId());
                assignment.setCategory(trademark.getCategory());
                assignment.setApplicant(trademark.getApplicant());
                assignment.setLegalPerson(org.getLegalPerson());
                assignment.setContact(contact.contact);
                assignment.setAddress(trademark.getAddress());
                assignment.setTrademark(trademark.getTrademark());
                assignment.setRegistrationNo(trademark.getRegistrationNo().toString());
                assignment.setDate(trademark.getDate());
                assignment.setTelNum(contact.telNum);
                assignment.setMobileNum(contact.mobileNum);
                assignment.setHasTel(contact.hasTel);

                if (org.getEmployeeId() != null) {
                    Employee employee = employeeDao.find(org.getEmployeeId() == null ? null : org.getEmployeeId().toString());
                    assignment.setAgain(true);
                    assignment.setAssignmentName(employee.getName());
                } else {
                    assignment.setAgain(false);
                    assignment.setAssignmentName(null);
                }

                assignmentDao.insert(assignment);

                checkCrawlerNum(date);
            } else {
                Assignment udpateAssignment = new Assignment();
                udpateAssignment.setId(assignment.getId());
                udpateAssignment.setTrademark(assignment.getTrademark() + ";\n" + trademark.getTrademark());
                udpateAssignment.setRegistrationNo(assignment.getRegistrationNo() + ";\n" + trademark.getRegistrationNo());

                assignmentDao.update(udpateAssignment);
            }
        }
    }

    /**
     * 如果超过每日抓取上限，则抓取下一天
     */
    private void checkCrawlerNum(String date) {
        String maxStr = crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_NUM);
        if (!StringUtils.isEmpty(maxStr)) {
            int count = assignmentDao.countByDate(date);
            int max = Integer.parseInt(maxStr);
            if (count >= max) {
                String nextDate = findNextDate(date);
                if (!StringUtils.isEmpty(nextDate)) {
                    crawlerConfigService.saveCrawlerDate(nextDate);
                }
            }
        }
    }

    private String findNextDate(String date) {
        String crawlerType = crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_TYPE);
        if (StringUtils.isEmpty(crawlerType) || CrawlerConfig.CRAWLER_TYPE_PARSE.equals(crawlerType)) {
            return findNextDateRemote(date);
        } else {
            return excelStatusDao.findNextDate(date);
        }
    }

    private String findNextDateRemote(String date) {

        Map<String,String> params = new HashMap<>();
        params.put("date", date);

        HttpResult httpResult = HttpUtils.httpGet(HttpConfig.TRADEMARK_HOST + "/v1/trademark/getNextDate", params);

        if (httpResult != null && httpResult.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return httpResult.getResult();
        } else {
            logger.error("findNextDateRemote http request failed! params: {}, httpResult: {}", params, httpResult);
            return "";
        }
    }

    public void assign(String batchNo, Map<String, Object> assignMap) {

        for (Map.Entry<String, Object> assignEntry : assignMap.entrySet()) {
            String name = assignEntry.getKey();
            int num = Integer.parseInt(assignEntry.getValue() + "");
            Employee employee = employeeDao.findByName(name);

            if (num <= 0) {
                logger.info("{} do not assign.", employee.getName());
                continue;
            }

            List<TrademarkExportVO> dataList = new ArrayList<>();
            List<String> trademarkIds = new ArrayList<>();
            List<TrademarkExportVO> trademarkExportVOList = trademarkDao.findOldCustomerNewChance(employee.getId(), batchNo);

            if (trademarkExportVOList != null && trademarkExportVOList.size() > 0) {
                dataList.addAll(trademarkExportVOList);
                num = num - trademarkExportVOList.size();
                trademarkIds.addAll(trademarkExportVOList.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));
            }

            if (num > 0) {
                List<TrademarkExportVO> newTrademarkExportVOList = trademarkDao.findNewCustomerChance(batchNo, 200);
                if (newTrademarkExportVOList != null && newTrademarkExportVOList.size() > 0) {
                    List<TrademarkExportVO> combineTrademarkExportVOList = combine(newTrademarkExportVOList, num, trademarkIds, name);
                    dataList.addAll(combineTrademarkExportVOList);
                    List<Long> orgIds = combineTrademarkExportVOList.stream().map(TrademarkExportVO::getOrgId).collect(Collectors.toList());
                    orgDao.updateEmployeeId(orgIds, employee.getId());
                }

                //TODO 同一公司机会分裂给两个人的情况处理
            }

            if (dataList.size() == 0) {
                logger.warn("can not assign for {}, dataList is empty", name);
                return;
            }

            //回写employeeId
            trademarkDao.updateEmployeeId(trademarkIds, employee.getId());

            List<Long> orgIds = dataList.stream().map(TrademarkExportVO::getOrgId).collect(Collectors.toList());
            List<Tel> tels = telDao.findByOrgIds(orgIds);
            List<Mobile> mobiles = mobileDao.findByOrgIds(orgIds);
            Map<Long, List<Tel>> telMap = tels.stream().collect(Collectors.groupingBy(Tel::getOrgId));
            Map<Long, List<Mobile>> mobileMap = mobiles.stream().collect(Collectors.groupingBy(Mobile::getOrgId));

            for (TrademarkExportVO trademarkExportVO : dataList) {

                StringBuilder contact = new StringBuilder();
                String tel = "";
                List<Tel> tels1 = telMap.get(trademarkExportVO.getOrgId());
                if (!CollectionUtils.isEmpty(tels1)) {
                    tel = tels1.stream()
                            .map(Tel::getTelNo)
                            .reduce((a, b) -> a + ";\n" + b).get();
                    if (!StringUtils.isEmpty(tel)) {
                        contact.append(tel);
                    }
                }

                String mobile = "";
                List<Mobile> mobiles1 = mobileMap.get(trademarkExportVO.getOrgId());
                if (!CollectionUtils.isEmpty(mobiles1)) {
                    mobile = mobiles1.stream()
                            .map(Mobile::getMobileNo)
                            .reduce((a, b) -> a + ";\n" + b).get();
                    if (!StringUtils.isEmpty(mobile)) {
                        if (!StringUtils.isEmpty(tel)) {
                            contact.append(";\n");
                        }
                        contact.append(mobile);
                    }
                }
                trademarkExportVO.setContact(contact.toString());
            }

            List<List<Object>> rows = dataList.stream().map(TrademarkExportVO::getExcelRow).collect(Collectors.toList());

            ExcelData excelData = new ExcelData();
            excelData.setName("分配机会表");
            excelData.setTitles(exportExcelHeads);
            excelData.setRows(rows);

            try {
                ExportExcelUtil.export(excelData, excelConfig.getExportRootPath() + batchNo + "/" + name + "机会分配表.xls");
            } catch (IOException e) {
                logger.error("excel export error, batchNo: {}, name: {}", batchNo, name, e);
            }
        }
    }

    private List<TrademarkExportVO> combine(List<TrademarkExportVO> originList, int num, List<String> trademarkIds, String assignmentName) {

        Map<Long, List<TrademarkExportVO>> map = originList.stream().collect(Collectors.groupingBy(TrademarkExportVO::getOrgId))
                .entrySet().stream().limit(num).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<TrademarkExportVO> returnList = new ArrayList<>();
        for (List<TrademarkExportVO> list : map.values()) {

            trademarkIds.addAll(list.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));

            TrademarkExportVO trademarkExportVO = list.get(0);
            String trademark = list.stream()
                    .map(TrademarkExportVO::getTrademark)
                    .reduce((a, b) -> a + ";\n" + b).get();
            String registrationNo = list.stream()
                    .map(TrademarkExportVO::getRegistrationNo)
                    .reduce((a, b) -> a + ";\n" + b).get();

            trademarkExportVO.setTrademark(trademark);
            trademarkExportVO.setRegistrationNo(registrationNo);
            trademarkExportVO.setAssignmentName(assignmentName);
            returnList.add(trademarkExportVO);
        }

        return returnList;
    }

    public Pagable<AssignVo> getAssignPage(Integer offset) {

        Pagable<AssignVo> pagable = new Pagable<>();
        Integer totalNum = assignmentDao.countGroup();
        Integer pageNum = 10;
        List<AssignVo> list = assignmentDao.findPage(offset, pageNum);

        pagable.setTotal(totalNum);
        pagable.setPageNo(offset / pageNum);
        pagable.setPageNum(pageNum);
        pagable.setRows(list);
        return pagable;
    }

    private Contact getContact(Long orgId) {
        Contact contact = new Contact();
        contact.hasTel = false;
        List<Tel> telList = telDao.findByOrgIds(Collections.singletonList(orgId));
        List<Mobile> mobileList = mobileDao.findByOrgIds(Collections.singletonList(orgId));
        StringBuilder contactBuilder = new StringBuilder();

        int telNum = 0;
        int mobileNum = 0;

        String tel = null;
        if (!CollectionUtils.isEmpty(telList)) {
            contact.hasTel = true;
            telNum = telList.size();
            tel = telList.stream()
                    .map(Tel::getTelNo)
                    .reduce((a, b) -> a + ";\n" + b).get();
            if (!StringUtils.isEmpty(tel)) {
                contactBuilder.append(tel);
            }
        }

        if (!CollectionUtils.isEmpty(mobileList)) {
            mobileNum = mobileList.size();
            String mobile = mobileList.stream()
                    .map(Mobile::getMobileNo)
                    .reduce((a, b) -> a + ";\n" + b).get();
            if (!StringUtils.isEmpty(mobile)) {
                if (!StringUtils.isEmpty(tel)) {
                    contactBuilder.append(";\n");
                }
                contactBuilder.append(mobile);
            }
        }

        contact.contact = contactBuilder.toString();
        contact.telNum = telNum;
        contact.mobileNum = mobileNum;

        return contact;
    }


    private class Contact {
        String contact;
        boolean hasTel;
        int telNum;
        int mobileNum;
    }


}
