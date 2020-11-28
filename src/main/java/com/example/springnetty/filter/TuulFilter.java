package com.example.springnetty.filter;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-25 10:33
 **/
public abstract class TuulFilter {
    abstract public String filterType();
    abstract public int filterOrder();
    abstract public void run() throws Exception;
}
