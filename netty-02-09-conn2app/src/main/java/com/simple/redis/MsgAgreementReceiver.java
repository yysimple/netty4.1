package com.simple.redis;

import com.alibaba.fastjson.JSON;
import com.simple.entity.MsgAgreement;
import com.simple.util.CacheUtil;
import com.simple.util.MsgUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:10
 **/
public class MsgAgreementReceiver extends AbstractReceiver {

    private Logger logger = LoggerFactory.getLogger(MsgAgreementReceiver.class);

    @Override
    public void receiveMessage(Object message) {
        logger.info("接收到PUSH消息：{}", message);
        MsgAgreement msgAgreement = JSON.parseObject(message.toString(), MsgAgreement.class);
        String toChannelId = msgAgreement.getToChannelId();
        Channel channel = CacheUtil.cacheChannel.get(toChannelId);
        if (null == channel) {
            return;
        }
        channel.writeAndFlush(MsgUtil.obj2Json(msgAgreement));
    }
}
