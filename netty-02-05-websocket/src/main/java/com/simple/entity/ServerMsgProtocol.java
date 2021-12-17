package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 服务端主体信息
 *
 * @author: WuChengXing
 * @create: 2021-12-17 17:06
 **/
@Data
public class ServerMsgProtocol {
    /**
     * 链接信息;1自发信息、2群发消息
     * @see com.simple.enums.SendTypeEnum
     */
    private int type;

    /**
     * 通信管道ID，实际使用中会映射成用户名
     */
    private String channelId;

    /**
     * 用户头像[模拟分配]
     */
    private String userHeadImg;

    /**
     * 通信消息
     */
    private String msgInfo;
}
