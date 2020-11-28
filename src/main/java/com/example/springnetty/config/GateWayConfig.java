package com.example.springnetty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-24 10:39
 **/
@Component
@ConfigurationProperties(prefix = "api-gateway")
public class GateWayConfig {
    private List<Map<String,Object>> route;

    public List<Map<String, Object>> getRoute() {
        return route;
    }

    public void setRoute(List<Map<String, Object>> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "GateWayConfig{" +
                "route=" + route +
                '}';
    }


}
