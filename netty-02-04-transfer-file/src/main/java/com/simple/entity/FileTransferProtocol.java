package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 文件传输协议
 *
 * @author: WuChengXing
 * @create: 2021-12-17 15:32
 **/
@Data
public class FileTransferProtocol {

    /**
     * 0请求传输文件、1文件传输指令、2文件传输数据
     */
    private Integer transferType;

    /**
     * 数据对象；(0)FileDescInfo、(1)FileBurstInstruct、(2)FileBurstData
     */
    private Object transferObj;
}
