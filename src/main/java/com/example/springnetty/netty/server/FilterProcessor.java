package com.example.springnetty.netty.server;

import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.Exception.TuulException;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

/**
 * @program: spring-netty
 * @description: 过滤器执行器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 09:40
 **/
@Slf4j
public class FilterProcessor {
    static FilterProcessor INSTANCE = new FilterProcessor();

    public static FilterProcessor getInstance() {
        return INSTANCE;
    }

    public static void setProcessor(FilterProcessor processor) {
        INSTANCE = processor;
    }

    public void postRoute() throws TuulException {

            try {
                runFilters("post");
            } catch (TuulException e) {
                throw e;
            } catch (Throwable e) {
                throw new TuulException("UNCAUGHT_EXCEPTION_IN_POST_FILTER_"+e.getClass().getName(),500);
            }

    }
    public void preRoute() throws TuulException {

            try {
                runFilters("pre");
            } catch (TuulException e) {
                throw e;
            } catch (Throwable e) {
                throw new TuulException("UNCAUGHT_EXCEPTION_IN_PRE_FILTER_"+e.getClass().getName(),500);
            }

    }
    public void route() throws TuulException {

            try {
                runFilters("route");
            } catch (TuulException e) {
              throw e;
            } catch (Throwable e) {
                throw new TuulException("UNCAUGHT_EXCEPTION_IN_ROUTE_FILTER_"+e.getClass().getName(),500);
            }

    }
    public void error() {

            try {
                runFilters("error");
            } catch (Throwable e) {
               log.error(e.getMessage(),e);
            }

    }


    private void runFilters(String sType) throws TuulException,Exception {
        List<TuulFilter> list = FilterLoader.getInstance().getFiltersByType(sType);

        if(list!=null) {

            list.sort(Comparator.comparing(TuulFilter::filterOrder));//升序排  java8
            for (int i = 0; i < list.size(); i++) {

                TuulFilter tuulFilter = list.get(i);

                tuulFilter.run();
            }
        }
    }
}
