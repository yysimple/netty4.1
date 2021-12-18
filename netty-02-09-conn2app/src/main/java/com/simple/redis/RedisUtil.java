package com.simple.redis;

import com.alibaba.fastjson.JSON;
import com.simple.entity.UserChannelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:00
 **/
@Service("redisUtil")
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void pushObj(UserChannelInfo userChannelInfo) {
        redisTemplate.opsForHash().put("client-users", userChannelInfo.getChannelId(), JSON.toJSONString(userChannelInfo));
    }

    public List<UserChannelInfo> popList() {
        List<Object> values = redisTemplate.opsForHash().values("client-users");
        if (null == values) {
            return new ArrayList<>();
        }
        // 拿到所有客户端连接到服务端的信息
        List<UserChannelInfo> userChannelInfoList = new ArrayList<>();
        for (Object strJson : values) {
            userChannelInfoList.add(JSON.parseObject(strJson.toString(), UserChannelInfo.class));
        }
        return userChannelInfoList;
    }

    public void remove(String channelId) {
        redisTemplate.opsForHash().delete("client-users", channelId);
    }

    public void clear() {
        redisTemplate.delete("client-users");
    }

}
