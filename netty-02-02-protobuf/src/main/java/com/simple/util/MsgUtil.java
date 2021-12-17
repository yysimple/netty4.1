package com.simple.util;

import com.simple.entity.MsgBody;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:27
 **/
public class MsgUtil {
    /**
     * 构建protobuf消息体
     */
    public static MsgBody buildMsg(String channelId, String msgInfo) {
        MsgBody.Builder msg = MsgBody.newBuilder();
        msg.setChannelId(channelId);
        msg.setMsgInfo(msgInfo);
        return msg.build();
    }
}
