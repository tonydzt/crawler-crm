package com.kasuo.crawler.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author douzhitong
 * @date 18/11/17
 */
@Repository
public class BaituDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> getDecodeMap() {
        return jdbcTemplate.queryForList("select regNumLen,lastIndex,spec,code,encode from source_baitu_code order by regNumLen,lastIndex,spec,code");
    }

    public List<Map<String,Object>> getDecodeMap2() {
        return jdbcTemplate.queryForList("SELECT firstIndex,code,encode FROM source_baitu_code2 ORDER BY firstIndex,code");
    }
}
