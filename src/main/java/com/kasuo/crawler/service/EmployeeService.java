package com.kasuo.crawler.service;

import com.kasuo.crawler.dao.mybatis.EmployeeDao;
import com.kasuo.crawler.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }
}
