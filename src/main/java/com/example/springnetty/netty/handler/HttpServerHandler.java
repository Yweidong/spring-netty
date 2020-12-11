package com.example.springnetty.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.springnetty.common.ResponseJson;
import com.example.springnetty.config.GateWayConfig;
import com.example.springnetty.config.SetScanner;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.Exception.TuulException;
import com.example.springnetty.netty.config.NettyConfig;
import com.example.springnetty.netty.dispatcher.RequestDispatcher;

import com.example.springnetty.netty.server.RequestContext;
import com.example.springnetty.netty.server.TuulRunner;
import com.example.springnetty.util.SpringContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.codec.http.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;


/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-23 14:16
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
  private TuulRunner tuulRunner =   new TuulRunner();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof FullHttpRequest) {

            try{
                tuulRunner.preRoute();
                tuulRunner.route();
                tuulRunner.postRoute();
            }  finally {
                //清除
                RequestContext.getCurrentContext().unset();
            }



        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        if(cause instanceof TuulException) {
            TuulException cause1 = (TuulException) cause;

            new ResponseJson(ctx, String.valueOf(JSONObject.toJSON(cause1))).response();
        }
        log.info(cause.getMessage());

        ctx.close();
    }
}
