package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
@Repository
public interface EmailDao {

    void insert(Email email);

    void batchInsert(List<Email> list);
}
