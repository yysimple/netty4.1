package com.simple.redis;

import com.simple.entity.MsgAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:11
 **/
@Service
public class Publisher {

    private final RedisTemplate<String, Object> redisMessageTemplate;

    @Autowired
    public Publisher(RedisTemplate<String, Object> redisMessageTemplate) {
        this.redisMessageTemplate = redisMessageTemplate;
    }

    public void pushMessage(String topic, MsgAgreement message) {
        redisMessageTemplate.convertAndSend(topic, message);
    }
}
