package com.example.springnetty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @program: spring-netty
 * @description:
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-11-30 09:27
 **/
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        float i = 1/0;
        return "hello world";
    }

    @GetMapping("/test")
    public HashMap<String,Object> test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","san");
        map.put("age",12);
        return map;
    }

    @PostMapping("/api1")
    public HashMap<String,Object> api1() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","san");
        map.put("age",12);
        return map;
    }

    @PostMapping("/api2")
    public String api2() {
        return "hahah";
    }

}
