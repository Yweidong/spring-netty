package com.example.springnetty.filter;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-netty
 * @description: 过滤器注册
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 10:21
 **/
public class FilterRegistry {
    private static final FilterRegistry INSTANCE = new FilterRegistry();

    public static final FilterRegistry instance() {
        return INSTANCE;
    }

    private final ConcurrentHashMap<String, TuulFilter> filters = new ConcurrentHashMap<String, TuulFilter>();

    private FilterRegistry() {
    }

    public TuulFilter remove(String key) {
        return this.filters.remove(key);
    }

    public TuulFilter get(String key) {
        return this.filters.get(key);
    }

    public void put(String key, TuulFilter filter) {
        this.filters.putIfAbsent(key, filter);
    }

    public int size() {
        return this.filters.size();
    }

    public Collection<TuulFilter> getAllFilters() {
        return this.filters.values();
    }
}
