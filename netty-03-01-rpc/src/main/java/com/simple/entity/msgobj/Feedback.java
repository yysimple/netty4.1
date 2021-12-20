package com.simple.entity.msgobj;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 12:58
 **/
@Data
public class Feedback {
    /**
     * 设备ID{管道ID}
     */
    private String channelId;

    /**
     * 状态类型;1\2
     */
    private Integer stateType;

    /**
     * 状态信息
     */
    private String stateMsg;

    public Feedback(String channelId, Integer stateType, String stateMsg) {
        this.channelId = channelId;
        this.stateType = stateType;
        this.stateMsg = stateMsg;
    }
}
