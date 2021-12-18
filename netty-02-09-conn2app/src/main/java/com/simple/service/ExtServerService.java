package com.simple.service;

import com.simple.entity.MsgAgreement;
import com.simple.redis.Publisher;
import com.simple.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能描述: 扩展服务
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:13
 **/
@Service("extServerService")
public class ExtServerService {
    @Resource
    private Publisher publisher;
    @Resource
    private RedisUtil redisUtil;

    public void push(MsgAgreement msgAgreement) {
        publisher.pushMessage("netty-push", msgAgreement);
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }
}
