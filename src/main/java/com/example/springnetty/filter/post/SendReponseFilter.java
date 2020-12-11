package com.example.springnetty.filter.post;

import com.example.springnetty.common.ResponseJson;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.Exception.TuulException;
import com.example.springnetty.netty.server.RequestContext;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-30 15:26
 **/
@Slf4j
public class SendReponseFilter extends TuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public void run() throws TuulException {
        RequestContext context = RequestContext.getCurrentContext();
        ChannelHandlerContext ctx = context.getChannelHandler();
        ResponseEntity responseEntity = context.getResponseEntity();
        String body =(String) responseEntity.getBody();

        try {
            new ResponseJson(ctx,body).response();
        } catch (TuulException e) {
           throw e;
        }

        log.info(body);
    }
}
