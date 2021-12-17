package com.simple.entity;

import com.simple.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 文件分片数据
 *
 * @author: WuChengXing
 * @create: 2021-12-17 14:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBurstData {
    /**
     * 客户端文件地址
     */
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 开始位置
     */
    private Integer beginPos;

    /**
     * 结束位置
     */
    private Integer endPos;

    /**
     * 文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全
     */
    private byte[] bytes;

    /**
     * Constants.FileStatus ｛0开始、1中间、2结尾、3完成｝
     *
     * @see Constants
     */
    private Integer status;

    public FileBurstData(Integer status) {
        this.status = status;
    }

}
