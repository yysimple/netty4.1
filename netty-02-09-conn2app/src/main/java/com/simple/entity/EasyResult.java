package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 接口反馈信息类
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:47
 **/
@Data
public class EasyResult {
    private boolean success;
    private String title;
    private String msg;

    static public EasyResult buildSuccessResult() {
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(true);
        easyResult.setMsg("ok");
        return easyResult;
    }

    static public EasyResult buildErrResult(Exception e) {
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(false);
        easyResult.setMsg(e.getMessage());
        return easyResult;
    }

    static public EasyResult buildErrResult(String msg) {
        EasyResult easyResult = new EasyResult();
        easyResult.setSuccess(false);
        easyResult.setMsg(msg);
        return easyResult;
    }

}
