package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Tel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
@Repository
public interface TelDao {

    void insert(Tel tel);

    void batchInsert(List<Tel> list);

    List<Tel> findByOrgIds(@Param("orgIds") List<Long> orgIds);
}
