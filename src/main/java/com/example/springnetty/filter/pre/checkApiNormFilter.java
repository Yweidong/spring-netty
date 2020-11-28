package com.example.springnetty.filter.pre;


import com.example.springnetty.common.ResponseJson;

import com.example.springnetty.common.Result;
import com.example.springnetty.common.ResultStatus;

import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.server.RequestContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: spring-netty
 * @description: 判断请求的接口是否符合要求配置文件中的  name 和 url
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-25 14:28
 **/

@Slf4j
public class checkApiNormFilter extends TuulFilter {


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public void run() throws Exception {
        RequestContext context = RequestContext.getCurrentContext();
        FullHttpRequest request = context.getFullHttpRequest();
        ChannelHandlerContext ctx = context.getChannelHandler();
        HashMap<String, Object> hashMap = context.getHashMap();
        String uri = request.uri();
        if(uri.equals("/")) {
            new ResponseJson<Result>(ctx, new Result(ResultStatus.PATH_ERROR,"请求地址不在配置文件中")).response();
        }

       String[] split = uri.replaceAll("\\?(.*)","").split("\\/");
        String path = split[1];

        int flag = 0;
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            if(path.equals(entry.getKey())) {
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
            new ResponseJson<Result>(ctx, new Result(ResultStatus.PATH_ERROR,"请求地址不在配置文件中")).response();
        }

        context.setRouteName(path);
        log.info("{}过滤器已检验通过","checkApiNormFilter");
        return;



    }
}
