package com.kasuo.crawler.service.crm;

import com.kasuo.crawler.domain.crm.SystemParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author douzhitong
 * @date 18/11/18
 */
@Service
public class SystemParamService {

    private static Map<String, SystemParam> map = new ConcurrentHashMap();


}
