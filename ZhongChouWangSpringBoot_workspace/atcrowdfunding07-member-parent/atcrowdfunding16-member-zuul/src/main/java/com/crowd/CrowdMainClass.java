package com.crowd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.util.ArrayList;

@EnableZuulProxy
@SpringBootApplication
public class  CrowdMainClass {




    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass.class, args);
    }

}
