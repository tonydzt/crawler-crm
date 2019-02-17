package com.kasuo.crawler.common;

import java.util.List;

/**
 * @author douzhitong
 * @date 2019/2/17
 */
public class Pagable<T> {

    private Integer total;
    private Integer pageNo;
    private Integer pageNum;
    private List<T> rows;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
