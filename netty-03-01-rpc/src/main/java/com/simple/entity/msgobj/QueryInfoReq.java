package com.simple.entity.msgobj;

import lombok.Data;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 13:52
 **/
@Data
public class QueryInfoReq {
    /**
     * 类型；Feedback=>{1\2}
     */
    private Integer stateType;

    public QueryInfoReq(Integer stateType) {
        this.stateType = stateType;
    }
}
