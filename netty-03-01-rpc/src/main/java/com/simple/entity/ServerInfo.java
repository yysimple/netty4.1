package com.simple.entity;

import lombok.Data;

import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-19 13:56
 **/
@Data
public class ServerInfo {
    /**
     * 服务类型
     */
    private String typeInfo;

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * 启动时间
     */
    private Date openDate;

    public ServerInfo(String typeInfo, String ip, int port, Date openDate) {
        this.typeInfo = typeInfo;
        this.ip = ip;
        this.port = port;
        this.openDate = openDate;
    }

}
