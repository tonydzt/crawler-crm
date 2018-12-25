package com.kasuo.crawler.domain;

/**
 * @author douzhitong
 * @date 2018/12/20
 */
public class ExcelStatus {

    /**
     * status字段字典
     * 0 - 未解析
     * 1 - 解析中
     * 2 - 已解析
     */
    public static final int UN_PARSE = 0;
    public static final int PARSING = 1;
    public static final int PARSED = 2;

    private Long id;
    private String path;
    private String fileName;
    private Integer status;
    private Integer totalNum;
    private Integer validNum;
    private Integer duplicateNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getValidNum() {
        return validNum;
    }

    public void setValidNum(Integer validNum) {
        this.validNum = validNum;
    }

    public Integer getDuplicateNum() {
        return duplicateNum;
    }

    public void setDuplicateNum(Integer duplicateNum) {
        this.duplicateNum = duplicateNum;
    }
}
