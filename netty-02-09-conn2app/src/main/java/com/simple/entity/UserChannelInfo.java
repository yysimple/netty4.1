package com.simple.entity;

import lombok.Data;

import java.util.Date;

/**
 * 功能描述: 用户管道信息；记录某个用户分配到某个服务端
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:51
 **/
@Data
public class UserChannelInfo {
    /**
     * 服务端：IP
     */
    private String ip;

    /**
     * 服务端：port
     */
    private int port;

    /**
     * channelId
     */
    private String channelId;

    /**
     * 连接的时间
     */
    private Date linkDate;

    public UserChannelInfo(String ip, int port, String channelId, Date linkDate) {
        this.ip = ip;
        this.port = port;
        this.channelId = channelId;
        this.linkDate = linkDate;
    }
}
