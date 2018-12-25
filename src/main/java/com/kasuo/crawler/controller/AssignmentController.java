package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@RestController
@RequestMapping(value = "/v1/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public Response assign(@RequestBody Map<String,Object> body) {

        String batchNo = body.get("batchNo").toString();
        Map<String,Object> assignMap = (Map<String, Object>) body.get("assign");

        assignmentService.assign(batchNo, assignMap);

        return ErrorCode.OK_EMPTY;
    }
}
