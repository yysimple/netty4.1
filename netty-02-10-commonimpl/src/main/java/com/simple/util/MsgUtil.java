package com.simple.util;

import com.simple.entity.MsgDemo01;
import com.simple.entity.MsgDemo02;
import com.simple.entity.MsgDemo03;

/**
 * 功能描述: 构建不同的消息体，用来适应不同的协议类型
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:27
 **/
public class MsgUtil {
    public static MsgDemo01 buildMsgDemo01(String channelId, String msgContent) {
        return new MsgDemo01(channelId, msgContent);
    }

    public static MsgDemo02 buildMsgDemo02(String channelId, String msgContent) {
        return new MsgDemo02(channelId, msgContent);
    }

    public static MsgDemo03 buildMsgDemo03(String channelId, String msgContent) {
        return new MsgDemo03(channelId, msgContent);
    }
}
