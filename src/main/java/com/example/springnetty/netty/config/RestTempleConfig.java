package com.example.springnetty.netty.config;

import com.example.springnetty.netty.server.FilterLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 14:21
 **/

public class RestTempleConfig {

    final static RestTempleConfig INSTANCE = new RestTempleConfig();
    public static RestTempleConfig getInstance() {
        return INSTANCE;
    }


    public RestTemplate restTemplate(){
        return new RestTemplate(simpleClientHttpRequestFactory());
    }


    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);//链接超时
        factory.setReadTimeout(5000);//读取超时
        return factory;
    }


}
