package com.kasuo.crawler.filter;

/**
 * @author douzhitong
 * @date 2019/1/2
 */
public interface Filter <T> {
    boolean accepts(T t);
}
