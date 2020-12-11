package com.example.springnetty.handler;

import com.example.springnetty.netty.Exception.TuulException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 14:55
 **/
@Slf4j
public class RestTempErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return true;
    }


    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {


    }
}
