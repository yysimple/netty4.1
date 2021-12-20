package com.simple.entity;

import lombok.Data;

import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 13:53
 **/
@Data
public class Device {

    /**
     * 通信管道ID
     */
    private String channelId;

    /**
     * 设备编号
     */
    private String number;

    /**
     * 设备IP
     */
    private String ip;

    /**
     * 设备端口
     */
    private int port;

    /**
     * 连接时间
     */
    private Date connectTime;
}
