package com.simple.msg;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:09
 **/
@Data
public class Request {
    private String requestId;
    private Object result;
}
