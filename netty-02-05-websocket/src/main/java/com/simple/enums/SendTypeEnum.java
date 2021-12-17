package com.simple.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 17:28
 **/
@Getter
@AllArgsConstructor
public enum SendTypeEnum {

    /**
     *
     */
    LINK_SEND(1, "链接发送"),
    BATCH_SEND(2, "群发"),
    ;

    private final Integer index;
    private final String desc;
}
