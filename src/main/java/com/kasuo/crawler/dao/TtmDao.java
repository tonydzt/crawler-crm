//package com.kasuo.crawler.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author douzhitong
// * @date 18/11/17
// */
//@Repository
//public class TtmDao {
//
//    @Autowired
//    @Qualifier("jdbcTemplateBt")
//    protected JdbcTemplate jdbcTemplate;
//
//    public List<Map<String,Object>> find() {
//        return jdbcTemplate.queryForList("select top 1 * from QSTMGood.dbo.tTM01");
//    }
//}
