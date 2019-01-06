package com.kasuo.crawler.service;

import com.kasuo.crawler.config.ExcelConfig;
import com.kasuo.crawler.dao.TrademarkDao;
import com.kasuo.crawler.dao.mybatis.*;
import com.kasuo.crawler.domain.Employee;
import com.kasuo.crawler.domain.ExcelStatus;
import com.kasuo.crawler.domain.Mobile;
import com.kasuo.crawler.domain.Tel;
import com.kasuo.crawler.domain.vo.TrademarkExportVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@Service
public class AssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentService.class);

    private static List<String> exportExcelHeads = Lists.newArrayList("客户名称(必填)",	"电话",	"地址",	"跟进状态",	"下次跟进时间",	"备注", "商标名称	", "注册号", "申请时间", "类别", "负责人", "联系人姓名", "联系人电话", "联系人手机", "机会类型");

    @Autowired
    private ExcelConfig excelConfig;

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

    public void assign(String batchNo, Map<String, Object> assignMap) {

        List<ExcelStatus> excelStatusList = excelStatusDao.findByPath(excelConfig.getRootPath() + batchNo);

        if (excelStatusList == null || excelStatusList.size() == 0) {
            logger.warn("assign failed, not found excelStatus. batchNo: {}", batchNo);
            return;
        }

        List<String> excelStatusIdStrs = excelStatusList.stream()
                .map(excelStatus -> excelStatus.getId().toString())
                .collect(Collectors.toList());

        for (Map.Entry<String,Object> assignEntry : assignMap.entrySet()) {
            String name = assignEntry.getKey();
            int num =  Integer.parseInt(assignEntry.getValue() + "");
            Employee employee = employeeDao.findByName(name);
            List<TrademarkExportVO> dataList = new ArrayList<>();
            List<String> trademarkIds = new ArrayList<>();
            List<TrademarkExportVO> trademarkExportVOList = trademarkDao.findOldCustomerNewChance(employee.getId(), excelStatusIdStrs);

            if (trademarkExportVOList != null && trademarkExportVOList.size() > 0) {
                dataList.addAll(trademarkExportVOList);
                num = num - trademarkExportVOList.size();
                trademarkIds.addAll(trademarkExportVOList.stream().map(a -> a.getId().toString()).collect(Collectors.toList()));
            }

            if (num > 0) {
                List<TrademarkExportVO> newTrademarkExportVOList = trademarkDao.findNewCustomerChance(excelStatusIdStrs, 200);
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

    private List<TrademarkExportVO> combine (List<TrademarkExportVO> originList, int num, List<String> trademarkIds, String assignmentName) {

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


}
