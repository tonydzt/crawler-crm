package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@Repository
public interface EmployeeDao {

    Employee findByName(String name);

    void insert(Employee employee);

    List<Employee> findAll();
}
