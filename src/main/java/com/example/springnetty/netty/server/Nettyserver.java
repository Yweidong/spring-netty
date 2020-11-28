package com.example.springnetty.netty.server;


import com.example.springnetty.netty.config.NettyConfig;


import com.example.springnetty.netty.constant.NettyConstant;
import com.example.springnetty.netty.handler.HttpServerHandler;
import com.example.springnetty.util.SpringContextUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-23 10:33
 **/
@Slf4j
@Component
public class Nettyserver {
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workGroup = new NioEventLoopGroup();

    private Channel channel;

    @Resource
    NettyConfig nettyConfig;





    /**
     *
     * 启动服务
     */
    public ChannelFuture start() {
        ChannelFuture f = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
//                                    pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstant.getMaxFrameLength(),
//                                            0,2,0,2));
//                                    pipeline.addLast(new LengthFieldPrepender(2));
                                    pipeline.addLast(new HttpServerCodec());
                                    pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器
                                    pipeline.addLast(new HttpServerHandler());
                                }
                            });
            log.info("netty服务器在[{}]端口启动监听",nettyConfig.getPort());


            f = serverBootstrap.bind(nettyConfig.getPort()).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e) {
            log.info("[出现异常]  释放资源");
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }finally {
            if(f!=null && f.isSuccess()) {
                log.info("Netty server listening "+ nettyConfig.getHostname()+ "on port "+ nettyConfig.getPort()+ " and ready for connections...");
            }else {
                log.info("Netty server start up Error");
            }
        }
        return f;
    }

    /**
     *
     *停止服务
     */
    public void destory() {
        log.info("Shutdown Netty Server...");
        if(channel!=null) {
            channel.close();
        }
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!!!");
    }
}
