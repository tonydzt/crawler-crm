package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Assignment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author douzhitong
 * @date 2019/1/13
 */
@Repository
public interface AssignmentDao {

    void insert(Assignment assignment);

    int update(Assignment assignment);

    Assignment findByOrgIdAndDate(@Param("orgId") Long orgId, @Param("date") String date);

    int countByDate(String date);
}
