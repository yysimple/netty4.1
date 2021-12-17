package com.simple.entity;

import lombok.Data;

/**
 * 功能描述: 文件描述信息
 *
 * @author: WuChengXing
 * @create: 2021-12-17 15:31
 **/
@Data
public class FileDescInfo {

    /**
     * 文件路径
     */
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;
}
