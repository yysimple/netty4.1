package com.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class MsgInfo {
    private String channelId;
    private String msgContent;
}
