package com.example.springnetty.netty.server;

import com.example.springnetty.filter.FilterRegistry;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.factory.DefaultFilterFactory;
import com.example.springnetty.netty.factory.FilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-netty
 * @description: 过滤器加载器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 09:43
 **/
@Slf4j
public class FilterLoader {
    final static FilterLoader INSTANCE = new FilterLoader();
    private static final ConcurrentHashMap<String, List<TuulFilter>> hashFiltersByType = new ConcurrentHashMap<>();


    public static FilterLoader getInstance() {
        return INSTANCE;
    }

    private FilterRegistry filterRegistry = FilterRegistry.instance();

    static FilterFactory FILTER_FACTORY = new DefaultFilterFactory();

    public void setFilterRegistry(FilterRegistry r) {
        this.filterRegistry = r;
    }

    public void setFilterFactory(FilterFactory factory) {
        FILTER_FACTORY = factory;
    }
    static {
        Reflections reflections = new Reflections("com.example.springnetty.filter");
        Set<Class<? extends TuulFilter>> subTypes = reflections.getSubTypesOf(TuulFilter.class);
        subTypes.forEach(aClass -> {
            try {
                INSTANCE.putFilter(aClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 注册 filter
     *
     */
    public  boolean putFilter(Class<? extends TuulFilter> aClass) throws Exception {
        String sName = aClass.getName();

        TuulFilter tuulFilter = filterRegistry.get(sName);
        if(tuulFilter == null) {
            tuulFilter = (TuulFilter) FILTER_FACTORY.newInstance(aClass);
            List<TuulFilter> list = hashFiltersByType.get(tuulFilter.filterType());
            if(list!=null) {
                hashFiltersByType.remove(tuulFilter.filterType());
            }
            filterRegistry.put(sName,tuulFilter);

            return true;
        }

        return false;
    }

    public List<TuulFilter> getFiltersByType(String filterType) {
        List list = hashFiltersByType.get(filterType);
        if(list != null) return list;
        list = new ArrayList<>();
        Collection<TuulFilter> filters = filterRegistry.getAllFilters();
        for (Iterator<TuulFilter> iterator = filters.iterator(); iterator.hasNext(); ) {
            TuulFilter filter = iterator.next();

            if (filter.filterType().equals(filterType)) {
                list.add(filter);
            }
        }
        hashFiltersByType.put(filterType,list);
        return list;
    }
}
