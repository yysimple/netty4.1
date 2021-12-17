package com.simple.util;

import com.alibaba.fastjson.JSON;
import com.simple.entity.ServerMsgProtocol;
import com.simple.enums.SendTypeEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 17:17
 **/
public class MsgUtil {
    public static TextWebSocketFrame buildMsgAll(String channelId, String msgInfo) {
        //模拟头像
        int i = Math.abs(channelId.hashCode()) % 10;

        ServerMsgProtocol msg = new ServerMsgProtocol();
        /**
         * 链接信息;1自发信息、2群发消息
         * @see com.simple.enums.SendTypeEnum
         */
        msg.setType(SendTypeEnum.BATCH_SEND.getIndex());
        msg.setChannelId(channelId);
        msg.setUserHeadImg("head" + i + ".jpg");
        msg.setMsgInfo(msgInfo);

        return new TextWebSocketFrame(JSON.toJSONString(msg));
    }

    public static TextWebSocketFrame buildMsgOwner(String channelId) {
        ServerMsgProtocol msg = new ServerMsgProtocol();
        // 链接信息;1链接信息、2消息信息
        msg.setType(SendTypeEnum.LINK_SEND.getIndex());
        msg.setChannelId(channelId);
        return new TextWebSocketFrame(JSON.toJSONString(msg));
    }

}
