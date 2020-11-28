package com.example.springnetty.netty.dispatcher;


import com.example.springnetty.netty.constant.NettyConstant;
import com.example.springnetty.netty.parser.RequestParser;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java.io.IOException;

import java.util.Map;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program: spring-netty
 * @description: 请求分发器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-24 10:51
 **/


public class RequestDispatcher{
    //创建线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());

    public void dispatcher(final ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws ExecutionException, InterruptedException {

       executorService.submit(() -> {

            ChannelFuture f = null;

            try {
                Map<String, String> parse = new RequestParser(fullHttpRequest).parse();
               return parse;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        });

    }
}
