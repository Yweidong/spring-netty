package com.example.springnetty.filter.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springnetty.filter.TuulFilter;
import com.example.springnetty.handler.RestTempErrorHandler;
import com.example.springnetty.netty.Exception.TuulException;
import com.example.springnetty.netty.config.RestTempleConfig;
import com.example.springnetty.netty.server.RequestContext;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @program: spring-netty
 * @description:  转发请求
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-30 09:09
 **/
@Slf4j
public class RequestRoutingFilter extends TuulFilter {

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public void run() throws TuulException {

        RequestContext context = RequestContext.getCurrentContext();
        RequestEntity requestEntity = context.getRequestEntity();

        RestTemplate restTemplate = RestTempleConfig.getInstance().restTemplate();
        restTemplate.setErrorHandler(new RestTempErrorHandler());
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        String body = responseEntity.getBody();
//        log.info(body);
        if(responseEntity.getStatusCode().isError()) {
            JSONObject jsonObject = JSON.parseObject(body);
            throw new TuulException((String) jsonObject.getString("error"),Integer.parseInt(String.valueOf(jsonObject.get("status"))));
        }
        log.info(responseEntity.toString());
        context.setResponseEntity(responseEntity);

    }
}
