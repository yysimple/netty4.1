package com.simple.entity;

import lombok.Data;

import java.util.Date;

/**
 * 功能描述: 服务端配置
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:50
 **/
@Data
public class ServerConfig {
    /**
     * 服务端IP
     */
    private String ip;

    /**
     * 服务端的端口
     */
    private int port;

    /**
     * 服务端的启动时间
     */
    private Date openDate;

    public ServerConfig(String ip, int port, Date openDate) {
        this.ip = ip;
        this.port = port;
        this.openDate = openDate;
    }
}
