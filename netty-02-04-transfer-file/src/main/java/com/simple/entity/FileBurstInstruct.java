package com.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 文件分片指令
 *
 * @author: WuChengXing
 * @create: 2021-12-17 15:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBurstInstruct {

    /**
     * Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
     * @see com.simple.constant.Constants
     */
    private Integer status;

    /**
     * 客户端文件URL
     */
    private String clientFileUrl;

    /**
     * 读取位置
     */
    private Integer readPosition;

    public FileBurstInstruct(Integer status) {
        this.status = status;
    }
}
