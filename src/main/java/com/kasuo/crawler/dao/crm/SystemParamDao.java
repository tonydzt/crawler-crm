//package com.kasuo.crawler.dao.crm;
//
//import com.kasuo.crawler.domain.crm.SystemParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author douzhitong
// * @date 18/11/18
// */
//@Repository
//public class SystemParamDao {
//
//    @Autowired
//    protected JdbcTemplate jdbcTemplate;
//
//    public List<SystemParam> findAll() {
//        return jdbcTemplate.queryForList("select * from system_param");
//    }
//
//}
