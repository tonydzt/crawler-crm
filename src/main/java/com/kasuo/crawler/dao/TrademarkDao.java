package com.kasuo.crawler.dao;

import com.kasuo.crawler.common.HttpResult;
import com.kasuo.crawler.config.HttpConfig;
import com.kasuo.crawler.dao.mybatis.CrawlerConfigDao;
import com.kasuo.crawler.domain.CrawlerConfig;
import com.kasuo.crawler.domain.Trademark;
import com.kasuo.crawler.domain.source.SourceUtil;
import com.kasuo.crawler.domain.vo.TrademarkExportVO;
import com.kasuo.crawler.util.HttpUtils;
import com.kasuo.crawler.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
@Repository
public class TrademarkDao {

    private static final Logger logger = LoggerFactory.getLogger(TrademarkDao.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected CrawlerConfigDao crawlerConfigDao;

    public boolean insert(Trademark trademark) {
        String sql = "insert into trademark (excel_status_id, category, registration_no, trademark, date, applicant, address, agent, service, status, mobile, tel) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object> params = new ArrayList<>();
        params.add(trademark.getExcelStatusId());
        params.add(trademark.getCategory());
        params.add(trademark.getRegistrationNo());
        params.add(trademark.getTrademark());
        params.add(trademark.getDate());
        params.add(trademark.getApplicant());
        params.add(trademark.getAddress());
        params.add(trademark.getAgent());
        params.add(trademark.getService());
        params.add(trademark.getStatus());
        params.add(trademark.getMobile());
        params.add(trademark.getTel());

        logger.debug("sql: {}, params: {}", sql, params);

        try {
            jdbcTemplate.update(sql, params.toArray());
            return false;
        } catch (DuplicateKeyException e) {
            logger.error("DuplicateKey {}", trademark.getRegistrationNo());
            return true;
        }
    }

    public List<Trademark> findUnCraw() {

        CrawlerConfig crawlerTypeConfig = crawlerConfigDao.findByKey(CrawlerConfig.CRAWLER_TYPE);
        List<Trademark> trademarkList;

        if (crawlerTypeConfig == null || crawlerTypeConfig.getValue().equals(CrawlerConfig.CRAWLER_TYPE_PARSE)) {
            trademarkList = findUnCrawRemote();
            if (CollectionUtils.isEmpty(trademarkList)) {
                trademarkList = findUnCrawLocal();
            }
        } else {
            trademarkList = findUnCrawLocal();
        }

        return trademarkList;
    }

    private List<Trademark> findUnCrawRemote() {
        CrawlerConfig crawlerConfig = crawlerConfigDao.findByKey(CrawlerConfig.CRAWLER_DATE);

        Map<String,String> params = new HashMap<>();
        params.put("date", crawlerConfig.getValue());
        params.put("num", "10");

        HttpResult httpResult = HttpUtils.httpGet(HttpConfig.TRADEMARK_HOST + "/v1/trademark/getList", params);

        if (httpResult != null && httpResult.getResponseCode() == HttpURLConnection.HTTP_OK) {
            List<Trademark> trademarkList = JsonUtils.decodeToList(httpResult.getResult(), Trademark.class);

            int size = trademarkList.size();

            trademarkList = trademarkList.stream()
                    .filter(trademark -> SourceUtil.checkValidSourceOrgName(trademark.getApplicant(), trademark.getAddress()))
                    .collect(Collectors.toList());

            if (size > 0 && CollectionUtils.isEmpty(trademarkList)) {
                //都过滤掉了，重新获取
                trademarkList = findUnCrawRemote();
            }
            return trademarkList;
        } else {
            logger.error("findUnCrawRemote http request failed! params: {}, httpResult: {}", params, httpResult);
            return null;
        }
    }


    private List<Trademark> findUnCrawLocal() {
        CrawlerConfig crawlerConfig = crawlerConfigDao.findByKey(CrawlerConfig.CRAWLER_DATE);

        String sql = "select * from trademark where craw_status = 0 " + (crawlerConfig == null ? "" : "and date = '" + crawlerConfig.getValue() + "'") + " limit 5";
        List<Trademark> trademarkList = jdbcTemplate.query(sql, getRowMapper());
        if (CollectionUtils.isEmpty(trademarkList)) {
            return null;
        }
        String trademarkIds = trademarkList.stream()
                .map(trademark -> trademark.getId().toString())
                .reduce((a, b) -> a + "," + b).get();
        String updateSql = "update trademark set craw_status = 1 where id in (" + trademarkIds + ")";
        jdbcTemplate.update(updateSql);

        return trademarkList;
    }

    public List<TrademarkExportVO> findOldCustomerNewChance(Long employeeId, String date, String province) {
        String sql = "SELECT min(id) id, group_concat(category) category, min(org_id) org_id, min(applicant) applicant, min(legal_person) legal_person, min(address) address, trademark, min(registration_no), min(registration_no) registration_no, min(date) date, min(is_again) is_again from " +
                "(SELECT trademark.id, trademark.category, trademark.org_id, applicant, legal_person, address, trademark, registration_no, date, is_again FROM trademark LEFT JOIN org " +
                " ON trademark.org_id = org.id AND trademark.is_again = 1 AND trademark.employee_id IS NULL AND trademark.date = '" + date + "'" +
                " WHERE org.has_contact = 1 AND org.employee_id = ?" +
                (StringUtils.isEmpty(province) ? "" : " and trademark.address like '%" + province + "%'") +
                ") n group by trademark";

        logger.info("findOldCustomerNewChance sql: {}, employeeId: {}", sql, employeeId);
        return jdbcTemplate.query(sql, new Object[]{employeeId}, getExportRowMapper());
    }

    public List<TrademarkExportVO> findNewCustomerChance(String date, Integer num, String province) {
        String sql = "SELECT min(id) id, group_concat(category) category, min(org_id) org_id, min(applicant) applicant, min(legal_person) legal_person, min(address) address, trademark, min(registration_no), min(registration_no) registration_no, min(date) date, min(is_again) is_again from " +
                "(SELECT trademark.id, trademark.category, trademark.org_id, applicant, legal_person, address, trademark trademark, registration_no, date, 0 is_again FROM trademark LEFT JOIN org" +
                " ON trademark.org_id = org.id AND trademark.employee_id IS NULL AND trademark.date = '" + date + "'" +
                " WHERE org.has_contact = 1 AND org.employee_id IS NULL " +
                (StringUtils.isEmpty(province) ? "" : " and trademark.address like '%" + province + "%'") +
                "ORDER BY applicant LIMIT ?) n group by trademark";

        logger.info("findNewCustomerChance sql: {}, num: {}", sql, num);
        return jdbcTemplate.query(sql, new Object[]{num}, getExportRowMapper());
    }

    private RowMapper<Trademark> getRowMapper() {
        return (resultSet, i) -> {
            Trademark trademark = new Trademark();
            trademark.setId(resultSet.getLong("id"));
            trademark.setCategory(resultSet.getInt("category"));
            trademark.setDate(resultSet.getString("date"));
            trademark.setAddress(resultSet.getString("address"));
            trademark.setRegistrationNo(resultSet.getInt("registration_no"));
            trademark.setTrademark(resultSet.getString("trademark"));
            trademark.setApplicant(resultSet.getString("applicant"));
            trademark.setAgain(resultSet.getBoolean("is_again"));
            return trademark;
        };
    }

    private RowMapper<TrademarkExportVO> getExportRowMapper() {
        return (resultSet, i) -> {
            TrademarkExportVO trademarkExportVO = new TrademarkExportVO();
            trademarkExportVO.setId(resultSet.getLong("id"));
            trademarkExportVO.setCategory(resultSet.getString("category"));
            trademarkExportVO.setOrgId(resultSet.getLong("org_id"));
            trademarkExportVO.setApplicant(resultSet.getString("applicant"));
            trademarkExportVO.setLegalPerson(resultSet.getString("legal_person"));
            trademarkExportVO.setAddress(resultSet.getString("address"));
            trademarkExportVO.setTrademark(resultSet.getString("trademark"));
            trademarkExportVO.setRegistrationNo(resultSet.getInt("registration_no") + "");
            trademarkExportVO.setDate(resultSet.getString("date"));
            trademarkExportVO.setAgain(resultSet.getBoolean("is_again"));
            return trademarkExportVO;
        };
    }

    public void updateCrawStatusSkip(Long trademarkId, Long orgId, Boolean isNew) {
        String sql = "update trademark set craw_status = 3, org_id = ?, is_again = ?  where id = ?";
        jdbcTemplate.update(sql, orgId, !isNew, trademarkId);
    }

    public void updateCrawStatus(Long trademarkId, Long orgId, Boolean isNew) {
        String sql = "update trademark set craw_status = 2, org_id = ?, is_again = ? where id = ?";
        jdbcTemplate.update(sql, orgId, !isNew, trademarkId);
    }

    public void updateEmployeeId(List<String> trademarkIds, Long employeeId) {
        String sql = "update trademark set employee_id = ? where id in (" + String.join(",", trademarkIds) + ")";
        jdbcTemplate.update(sql, employeeId);
    }

    public Trademark find(Long trademarkId) {
        String sql = "select * from trademark where id = " + trademarkId;
        return jdbcTemplate.queryForObject(sql, getRowMapper());
    }
}
