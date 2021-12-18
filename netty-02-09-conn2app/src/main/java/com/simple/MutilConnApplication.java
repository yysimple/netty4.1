package com.simple;

import com.simple.redis.RedisUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:58
 **/
@SpringBootApplication
public class MutilConnApplication implements CommandLineRunner {
    @Resource
    private RedisUtil redisUtil;

    public static void main(String[] args) {
        SpringApplication.run(MutilConnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisUtil.clear();
    }
}
