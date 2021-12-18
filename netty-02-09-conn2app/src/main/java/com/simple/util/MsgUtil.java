package com.simple.util;

import com.alibaba.fastjson.JSON;
import com.simple.entity.MsgAgreement;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:56
 **/
public class MsgUtil {

    public static MsgAgreement buildMsg(String channelId, String content) {
        return new MsgAgreement(channelId, content);
    }

    public static MsgAgreement json2Obj(String objJsonStr) {
        return JSON.parseObject(objJsonStr, MsgAgreement.class);
    }

    public static String obj2Json(MsgAgreement msgAgreement) {
        return JSON.toJSONString(msgAgreement) + "\r\n";
    }
}
