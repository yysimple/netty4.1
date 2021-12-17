package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 客户端连接信息
 *
 * @author: WuChengXing
 * @create: 2021-12-17 17:06
 **/
@Data
public class ClientMsgProtocol {
    /**
     * 1 请求个人信息，2 发送聊天信息
     * @see com.simple.enums.SendTypeEnum
     */
    private int type;

    /**
     * 信息主题
     */
    private String msgInfo;
}
