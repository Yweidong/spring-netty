package com.example.springnetty.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.netty.parser.RequestParser;
import com.example.springnetty.netty.server.RequestContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @program: spring-netty
 * @description:  前置执行过滤器，负责对请求的数据的封装
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-25 13:35
 **/
@Slf4j
public class RequestWrapperFilter extends TuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public void run() {

        RequestContext context = RequestContext.getCurrentContext();
        String routeName = context.getRouteName();
        HashMap<String, Object> hashMap = context.getHashMap();

        FullHttpRequest fullHttpRequest = context.getFullHttpRequest();
        String apipath = fullHttpRequest.uri().replace("/" + routeName, "")
                .replaceAll("\\?(.*)", "");
        String rootURL = (String) hashMap.get(routeName)+apipath;//获取请求远程的url
        RequestEntity<String> requestEntity = null;
        try {
            requestEntity = createRequestEntity(fullHttpRequest,rootURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(requestEntity.toString());
        context.setRequestEntity(requestEntity);

        return;
    }

    private RequestEntity createRequestEntity(FullHttpRequest fullHttpRequest, String rootURL) throws Exception {
        String name = fullHttpRequest.method().name();
        HttpMethod method = HttpMethod.resolve(name);

        /**
         * MultiValueMap 一个key可以存多个value
         *
         */
        MultiValueMap<String, String> headers =createRequestHeaders(fullHttpRequest);
        String requestParam = createRequestParam(fullHttpRequest);
        RequestEntity<String> requestEntity = new RequestEntity<String>(requestParam, headers, method, new URI(rootURL));
        return requestEntity;
    }

    private String createRequestParam(FullHttpRequest fullHttpRequest) throws IOException {
        Map<String, String> parse = new RequestParser(fullHttpRequest).parse();
        String jsonString = JSONObject.toJSONString(parse);
        return jsonString;
    }

    private MultiValueMap<String, String> createRequestHeaders(FullHttpRequest fullHttpRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();

        Set<String> names = fullHttpRequest.headers().names();
        names.forEach(s -> {
            httpHeaders.add(s,fullHttpRequest.headers().get(s));
        });
        return httpHeaders;

    }
}
