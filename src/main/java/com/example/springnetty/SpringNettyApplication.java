package com.example.springnetty;

import com.example.springnetty.netty.server.Nettyserver;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringNettyApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringNettyApplication.class, args);
    }


}
