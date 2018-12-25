package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Employee;
import org.springframework.stereotype.Repository;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@Repository
public interface EmployeeDao {

    Employee findByName(String name);

    void insert(Employee employee);
}
