package com.example.springnetty.netty.constant;

import org.springframework.stereotype.Component;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-23 11:34
 **/
@Component
public class NettyConstant {

    /**
     * 最大线程量
     */
    private static final int MAX_THREADS = 1024;
    /**
     * 数据包最大长度
     */
    private static final int MAX_FRAME_LENGTH = 65535;

    public static int getMaxThreads() {
        return MAX_THREADS;
    }

    public static int getMaxFrameLength() {
        return MAX_FRAME_LENGTH;
    }


}
