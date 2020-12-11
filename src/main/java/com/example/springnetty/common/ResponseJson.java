package com.example.springnetty.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.springnetty.netty.Exception.TuulException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-26 09:57
 **/
@Slf4j
public class ResponseJson {
    private ChannelHandlerContext ctx;
    private String data;
    public ResponseJson(ChannelHandlerContext ctx, String data) {
        this.ctx = ctx;
        this.data= data;
    }

    public  void response() throws TuulException {
        try{
            JSONObject jsonObject = JSONObject.parseObject(data);
            ByteBuf byteBuf = Unpooled.copiedBuffer(data, CharsetUtil.UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,byteBuf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");//设置响应头
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            this.ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);//返回响应到客户端
        }catch (RuntimeException e) {
            throw new TuulException("can not cast to JSONObject.",500);


        }



    }



}
