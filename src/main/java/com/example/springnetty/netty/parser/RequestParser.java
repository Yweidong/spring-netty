package com.example.springnetty.netty.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: spring-netty
 * @description: 请求参数解析器
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-24 11:42
 **/
public class RequestParser {
    private FullHttpRequest freq;


    public RequestParser(FullHttpRequest req) {
        this.freq = req;
    }

    public Map<String, String> parse() throws IOException {
        HttpMethod method = freq.method();
        Map<String, String> parmMap = new HashMap<>();
        if(HttpMethod.GET == method) {
            QueryStringDecoder decoder = new QueryStringDecoder(freq.uri());
            decoder.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        }else if(HttpMethod.POST == method) {
            String contentType = freq.headers().get("Content-Type");
            if(contentType.equals("application/json")) {
                String s = freq.content().toString(Charset.forName("utf-8"));
                JSONObject jsonObject = JSONArray.parseObject(s);

                for (Map.Entry<String, Object> item : jsonObject.entrySet()) {

                    parmMap.put(item.getKey(),item.getValue().toString());

                }
            }else {
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(freq);

                decoder.offer(freq);

                List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

                for (InterfaceHttpData parm : parmList) {

                    Attribute data = (Attribute) parm;
                    parmMap.put(data.getName(), data.getValue());
                }
            }

        }
        return parmMap;
    }

}
