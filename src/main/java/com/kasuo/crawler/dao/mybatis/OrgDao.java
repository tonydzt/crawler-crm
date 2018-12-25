package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
@Repository
public interface OrgDao {

    void insert(Org org);

    Org findByNameForUpdate(String orgName);

    void updateHasContact(Long id);

    void updateEmployeeId(@Param("orgIds") List<Long> orgIds, @Param("employeeId") Long employeeId);
}
