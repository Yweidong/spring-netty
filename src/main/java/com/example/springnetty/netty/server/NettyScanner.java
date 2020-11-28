package com.example.springnetty.netty.server;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-23 14:02
 **/
@Component
public class NettyScanner implements CommandLineRunner {
    @Autowired
    private Nettyserver nettyserver;
    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = nettyserver.start();
        /**
         *在jvm中增加一个关闭的钩子，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子
         *          当系统执行完这些钩子后，jvm才会关闭。所以这些钩子可以在jvm关闭的时候进行内存清理、对象销毁等操作
         */

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                nettyserver.destory();
            }
        });

        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}
