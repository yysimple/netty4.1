package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 消息协议
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:49
 **/
@Data
public class MsgAgreement {
    /**
     * 发送给某人，某人channelId
     */
    private String toChannelId;

    /**
     * 消息内容
     */
    private String content;

    public MsgAgreement() {
    }

    public MsgAgreement(String toChannelId, String content) {
        this.toChannelId = toChannelId;
        this.content = content;
    }

}
