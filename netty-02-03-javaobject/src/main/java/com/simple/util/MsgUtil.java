package com.simple.util;

import com.simple.entity.MsgInfo;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:27
 **/
public class MsgUtil {
    /**
     * 构建MsgInfo消息体
     */
    public static MsgInfo buildMsg(String channelId, String msgContent) {
        return new MsgInfo(channelId, msgContent);
    }
}
