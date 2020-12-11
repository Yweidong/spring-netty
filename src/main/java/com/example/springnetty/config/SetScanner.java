package com.example.springnetty.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-26 14:01
 **/
@Slf4j
@Component
public class SetScanner {
    @Autowired
    GateWayConfig gateWayConfig;

    public HashMap<String,Object> run() throws Exception {
        List<Map<String, Object>> route = gateWayConfig.getRoute();
        HashMap<String, Object> map = new HashMap<>();
        route.forEach(stringObjectMap -> {
            map.put((String) stringObjectMap.get("name"),stringObjectMap.get("url"));

        });

        return map;
    }
}
