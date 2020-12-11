package com.example.springnetty.netty.factory;

import com.example.springnetty.filter.TuulFilter;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 10:55
 **/
public class DefaultFilterFactory implements FilterFactory {
    @Override
    public TuulFilter newInstance(Class clazz) throws Exception {
        return (TuulFilter) clazz.newInstance();
    }
}
