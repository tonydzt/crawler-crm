package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Mobile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
@Repository
public interface MobileDao {

    void insert(Mobile mobile);

    void batchInsert(List<Mobile> list);

    List<Mobile> findByOrgIds(@Param("orgIds") List<Long> orgIds);
}
