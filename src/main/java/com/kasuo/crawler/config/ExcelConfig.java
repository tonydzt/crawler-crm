package com.kasuo.crawler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author douzhitong
 * @date 2018/12/20
 */
@Component
@ConfigurationProperties(prefix = "excel")
public class ExcelConfig {

    private String rootPath;
    private String exportRootPath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getExportRootPath() {
        return exportRootPath;
    }

    public void setExportRootPath(String exportRootPath) {
        this.exportRootPath = exportRootPath;
    }
}
