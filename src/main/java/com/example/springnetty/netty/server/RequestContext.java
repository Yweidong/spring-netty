package com.example.springnetty.netty.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-netty
 * @description:通过每个线程单独一份存储空间，牺牲空间来解决冲突，并且相比于Synchronized，ThreadLocal具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问到想要的值。
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-25 10:47
 **/
public class RequestContext extends ConcurrentHashMap<String,Object> {
    protected static Class<? extends RequestContext> contextClass = RequestContext.class;
    protected static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {

        @Override
        protected RequestContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static RequestContext getCurrentContext() {
        RequestContext context = threadLocal.get();
        return context;
    }

    public ChannelHandlerContext getChannelHandler() {
        return (ChannelHandlerContext) get("ctx");
    }

    public void setChannelHandler(ChannelHandlerContext ctx) {
        set("ctx",ctx);
    }

    //储存http请求
    public FullHttpRequest getFullHttpRequest() {
        return (FullHttpRequest) get("request");
    }

    public void setFullHttpRequest(FullHttpRequest fullHttpRequest) {
        set("request",fullHttpRequest);
    }

    //储存配置文件中的name 和 url
    public HashMap<String,Object> getHashMap() {
        return (HashMap<String,Object>) get("map");
    }
    public void setHashMap(HashMap<String,Object> map) {
        set("map",map);
    }

    //储存当前请求的路由
    public String getRouteName() {
        return (String) get("route_name");
    }
    public void setRouteName(String route_name) {
        set("route_name",route_name);
    }

    //储存RestTemplate能识别的RequestEntity
    public RequestEntity getRequestEntity() {
        return (RequestEntity) get("requestEntity");
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        set("requestEntity",requestEntity);
    }

    //储存响应
    public ResponseEntity getResponseEntity() {
        return (ResponseEntity) get("responseEntity");
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        set("responseEntity",responseEntity);
    }

    public void set(String key,Object value) {
        if(value!=null) {
            put(key,value);
        }else {
            remove(key);
        }
    }

    public void unset() {
        threadLocal.remove();
    }

}
