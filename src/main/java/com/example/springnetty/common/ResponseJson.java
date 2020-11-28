package com.example.springnetty.common;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-26 09:57
 **/
public class ResponseJson<T> {
    private ChannelHandlerContext ctx;
    private T data;
    public ResponseJson(ChannelHandlerContext ctx, T data) {
        this.ctx = ctx;
        this.data= data;
    }

    public void response() {
        String s = JSON.toJSONString(data);
        ByteBuf byteBuf = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");//设置响应头
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

         this.ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//返回响应到客户端
    }



}
