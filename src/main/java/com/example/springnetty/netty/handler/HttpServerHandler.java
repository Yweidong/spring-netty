package com.example.springnetty.netty.handler;

import com.example.springnetty.config.GateWayConfig;
import com.example.springnetty.config.SetScanner;
import com.example.springnetty.filter.TuulFilter;
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
      @Autowired
      SetScanner setScanner;
    private static HttpServerHandler httpServerHandler;
    @PostConstruct
    public void init() {
        httpServerHandler= this;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof FullHttpRequest) {
            HashMap<String, Object> map = httpServerHandler.setScanner.run();
            tuulRunner.init(ctx, (FullHttpRequest)msg,map);
            try{
                tuulRunner.preRoute();
                tuulRunner.route();
                tuulRunner.postRoute();
            }finally {
                //清除
                RequestContext.getCurrentContext().unset();
            }


        }
    }

    //    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
//        if(httpObject instanceof FullHttpRequest) {
////            Map<String, String> parse = new RequestParser((FullHttpRequest) httpObject).parse();
////            System.out.println(parse);
//            requestDispatcher.dispatcher(channelHandlerContext, (FullHttpRequest)httpObject);//请求分发
////            String name = ((HttpRequest) httpObject).method().name();//请求方式
////            System.out.println(name);
////            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
////            //构造http响应
////            FullHttpResponse fullHttpResponse= new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
////                    HttpResponseStatus.OK, byteBuf);
////            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");//设置响应头
////            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
////            channelHandlerContext.writeAndFlush(fullHttpResponse);//返回响应到客户端
//        }
//    }
}
