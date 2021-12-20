package com.simple.entity;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 13:55
 **/
@Data
public class InfoProtocol {

    /**
     * 消息传输给某个，管道ID
     */
    private String channelId;

    /**
     * 消息类型；1、Text 2、QueryInfoReq 3、Feedback
     */
    private Integer msgType;

    /**
     * 消息对象
     */
    private Object msgObj;

}
