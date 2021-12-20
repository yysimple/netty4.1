package com.simple.util;

import com.alibaba.fastjson.JSON;
import com.simple.entity.InfoProtocol;
import com.simple.entity.msgobj.MsgText;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:56
 **/
public class MsgUtil {

    public static String buildMsg(InfoProtocol infoProtocol) {
        return JSON.toJSONString(infoProtocol) + "\r\n";
    }

    public static InfoProtocol getMsg(String str) {
        return JSON.parseObject(str, InfoProtocol.class);
    }

    public static TextWebSocketFrame buildWsMsgText(String channelId, String msgInfo) {
        InfoProtocol infoProtocol = new InfoProtocol();
        infoProtocol.setChannelId(channelId);
        infoProtocol.setMsgType(1);
        infoProtocol.setMsgObj(new MsgText(msgInfo));
        return new TextWebSocketFrame(JSON.toJSONString(infoProtocol));
    }
}
