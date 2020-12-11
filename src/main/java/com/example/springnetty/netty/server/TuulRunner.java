package com.example.springnetty.netty.server;

import com.example.springnetty.config.GateWayConfig;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.Exception.TuulException;
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


    public void init(ChannelHandlerContext ctx, FullHttpRequest freq,HashMap<String,Object> map) {

        RequestContext context = RequestContext.getCurrentContext();
        context.setChannelHandler(ctx);
        context.setFullHttpRequest(freq);
        context.setHashMap(map);

    }

    public void preRoute() throws TuulException {
        FilterProcessor.getInstance().preRoute();
    }
    public void route() throws TuulException {
        FilterProcessor.getInstance().route();
    }
    public void postRoute() throws TuulException {
       FilterProcessor.getInstance().postRoute();
    }




}
