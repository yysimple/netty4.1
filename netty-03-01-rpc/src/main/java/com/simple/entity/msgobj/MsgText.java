package com.simple.entity.msgobj;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 13:52
 **/
@Data
public class MsgText {

    private String msg;

    public MsgText(String msg) {
        this.msg = msg;
    }
}
