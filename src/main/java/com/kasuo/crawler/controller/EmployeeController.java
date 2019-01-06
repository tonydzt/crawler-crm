package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.common.ResponseData;
import com.kasuo.crawler.domain.Employee;
import com.kasuo.crawler.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@RestController
@RequestMapping(value = "/v1/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response save(@RequestBody Map<String,Object> body) {
        String name = body.get("name").toString();
        Employee employee = new Employee();
        employee.setName(name);
        employeeService.insert(employee);
        return ErrorCode.OK_EMPTY;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<Employee> findAll() {
        return employeeService.findAll();
    }
}
