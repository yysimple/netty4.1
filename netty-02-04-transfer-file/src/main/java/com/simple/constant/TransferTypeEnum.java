package com.simple.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 15:35
 **/
@Getter
@AllArgsConstructor
public enum TransferTypeEnum {

    /**
     *
     */
    REQUEST_TRANSFER_FILE(0, "请求传输文件"),
    FILE_TRANSFER_INSTRUCT(1, "文件传输指令"),
    FILE_TRANSFER_DATA(2, "文件传输数据"),
    ;

    private final Integer index;
    private final String desc;

}
