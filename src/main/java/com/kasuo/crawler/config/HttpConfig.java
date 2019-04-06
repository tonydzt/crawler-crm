package com.kasuo.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author dzt
 */
@Configuration
public class HttpConfig {

    public static String TRADEMARK_HOST;

    /**
     * trademarkHost 服务地址
     * @param trademarkHost  trademarkHost 服务地址
     */
    @Value("${trademark.host}")
    public void trademarkHostConfig(String trademarkHost) {
        TRADEMARK_HOST = trademarkHost;
    }
}
