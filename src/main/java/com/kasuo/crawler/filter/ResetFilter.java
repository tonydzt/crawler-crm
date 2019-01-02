package com.kasuo.crawler.filter;

/**
 * @author douzhitong
 * @date 2018/11/20
 */
public interface ResetFilter<T> extends Filter<T> {

    /**
     * 重置
     */
    void reset();
}
