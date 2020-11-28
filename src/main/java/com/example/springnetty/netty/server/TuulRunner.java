package com.example.springnetty.netty.server;

import com.example.springnetty.config.GateWayConfig;
import com.example.springnetty.filter.TuulFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-netty
 * @description: 执行器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-25 10:40
 **/
@Slf4j
public class TuulRunner {

    private static final ConcurrentHashMap<String, List<TuulFilter>> hashFiltersByType = new ConcurrentHashMap<>();


    static {

            Reflections reflections = new Reflections("com.example.springnetty.filter");
            Set<Class<? extends TuulFilter>> subTypes = reflections.getSubTypesOf(TuulFilter.class);
            ArrayList<TuulFilter> preFilter = new ArrayList<>();
            ArrayList<TuulFilter> routeFilter = new ArrayList<>();
            ArrayList<TuulFilter> postFilter = new ArrayList<>();

            subTypes.forEach(aClass -> {

                try {
                    if(aClass.newInstance().filterType().equals("pre")) {
                        preFilter.add(aClass.newInstance());
                    }

                if(aClass.newInstance().filterType().equals("route")) {
                    routeFilter.add(aClass.newInstance());
                }if(aClass.newInstance().filterType().equals("post")) {
                    postFilter.add(aClass.newInstance());
                }
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            });
            hashFiltersByType.put("pre",preFilter);
            hashFiltersByType.put("route",routeFilter);
            hashFiltersByType.put("post",postFilter);

    }

    public void init(ChannelHandlerContext ctx, FullHttpRequest freq,HashMap<String,Object> map) {

        RequestContext context = RequestContext.getCurrentContext();
        context.setChannelHandler(ctx);
        context.setFullHttpRequest(freq);
        context.setHashMap(map);

    }

    public void preRoute() throws Exception {
        runFilters("pre");
    }public void route() throws Exception {
        runFilters("route");
    }public void postRoute() throws Exception {
        runFilters("post");
    }


    private void runFilters(String sType) throws Exception {
        List<TuulFilter> list = hashFiltersByType.get(sType);
        if(list!=null) {

            list.sort(Comparator.comparing(TuulFilter::filterOrder));//升序排  java8
            for (int i = 0; i < list.size(); i++) {

                TuulFilter tuulFilter = list.get(i);

                tuulFilter.run();
            }
        }
    }

}
