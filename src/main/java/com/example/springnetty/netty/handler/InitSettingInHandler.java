package com.example.springnetty.netty.handler;

import com.example.springnetty.config.SetScanner;
import com.example.springnetty.netty.server.TuulRunner;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @program: spring-netty
 * @description: 初始化配置
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-30 13:54
 **/
@Component
@ChannelHandler.Sharable
@Slf4j
public class InitSettingInHandler extends ChannelInboundHandlerAdapter {
    private TuulRunner tuulRunner =   new TuulRunner();
    @Autowired
    SetScanner setScanner;
    private static InitSettingInHandler initSettingInHandler;
    @PostConstruct
    public void init() {
        initSettingInHandler= this;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HashMap<String, Object> map = initSettingInHandler.setScanner.run();
        tuulRunner.init(ctx, (FullHttpRequest)msg,map);

        ctx.fireChannelRead(msg);// 通知执行下一个InboundHandler
    }
}
