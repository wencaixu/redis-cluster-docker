package com.uvideo.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisClusterController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/clusterOperate")
    public String clusterOperate(){
        redisTemplate.opsForValue().set("caige","bb");

        return String.valueOf(redisTemplate.opsForValue().get("caige"));
    }

}
